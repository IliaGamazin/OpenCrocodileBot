package app.bot.config;

import app.authentication.*;
import app.authentication.client.TelegramClient;
import app.authentication.sessions.SessionHandler;
import app.bot.CrocodileBot;
import app.commands.controllers.callbacks.*;
import app.commands.controllers.commands.*;
import app.commands.controllers.proxies.ExceptionProxy;
import app.commands.dto.*;
import app.commands.handlers.*;
import app.commands.middleware.*;
import app.exceptions.PipelineException;
import app.game.GameHandler;
import app.routers.Router;
import app.services.randomword.WordProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(CrocodileBot bot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        return api;
    }

    @Bean
    public GameHandler gameHandler(WordProvider provider) {
        return new GameHandler(provider);
    }

    @Bean
    public CommandRepo commandRepo(TelegramClient client,
                                   GameHandler games,
                                   SessionHandler sessions,
                                   ExceptionProxy proxy) {
        CommandRepo repo = new CommandHandler();

        repo.register("run", new RunController(client, games, proxy));
        repo.register("give_up", new GiveUpController(client, games, proxy));
        repo.register("language", new LanguageController(client, proxy));
        repo.register("message", new MessageController(client, games, proxy));

        repo.register("language-callback", new LanguageButtonController(sessions, client, proxy));
        repo.register("see-callback", new SeeButtonController(client, games, proxy));
        repo.register("next-callback", new NextButtonController(client, games, proxy));
        repo.register("claim-callback", new ClaimButtonController(client, games, proxy));

        return repo;
    }

    @Bean
    public Pipeline pipeline(TelegramClient client, SessionHandler sessions) {
        List<Middleware<UnAuthedConfig, PipelineException>> preAuthMiddlewares = List.of(new ErrorHandler(client), new LoggerMiddleware());
        List<Middleware<AuthedConfig, PipelineException>> postAuthMiddlewares = new ArrayList<>();

        MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain = new MiddlewareChain<>(preAuthMiddlewares);
        MiddlewareChain<AuthedConfig, PipelineException> postAuthChain = new MiddlewareChain<>(postAuthMiddlewares);

        AuthBridge bridge = new Authenticator(sessions);

        return new MiddlewarePipeline(preAuthChain, postAuthChain, bridge);
    }

    @Bean
    CrocodileBot crocodileBot(@Value("${bot.token}") String token,
                                    @Value("${bot.name}") String name,
                                    Router router,
                                    TelegramClient client) {
        CrocodileBot bot = new CrocodileBot(token, name, router);
        client.setExecute(bot::execute);
        return bot;
    }
}
