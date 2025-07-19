package bot;

import authentication.AuthBridge;
import authentication.Authenticator;
import bot.config.AuthedConfig;
import bot.config.BotConfig;
import bot.config.UnAuthedConfig;
import commands.middleware.*;
import authentication.client.TelegramBotClient;
import commands.handlers.CommandHandler;
import commands.handlers.Handler;
import exceptions.PipelineException;
import routers.Router;
import routers.UpdateRouter;
import authentication.client.TelegramClient;
import game.GameHandler;
import services.parsers.Parser;
import services.parsers.UniversalParser;
import services.randomword.WordProvider;
import services.randomword.wiktionary.WiktionaryProvider;
import authentication.sessions.SessionHandler;

import java.util.ArrayList;
import java.util.List;

public class BotFactory {
    public BotFactory() {}

    public CrocodileBot createBot(String token, String name) {
        TelegramClient client = new TelegramBotClient();
        WordProvider provider = new WiktionaryProvider();

        SessionHandler sessions = new SessionHandler();
        GameHandler games = new GameHandler(provider);

        Handler handler = new CommandHandler(sessions, games, client);
        Parser parser = new UniversalParser(name);

        List<Middleware<UnAuthedConfig, PipelineException>> preAuthMiddlewares = new ArrayList<>();
        List<Middleware<AuthedConfig, PipelineException>> postAuthMiddlewares = new ArrayList<>();
        preAuthMiddlewares.add(new LoggerMiddleware());

        MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain = new MiddlewareChain<>(preAuthMiddlewares);
        MiddlewareChain<AuthedConfig, PipelineException> postAuthChain = new MiddlewareChain<>(postAuthMiddlewares);

        AuthBridge bridge = new Authenticator(sessions);

        Pipeline pipeline = new Pipeline(preAuthChain, postAuthChain, bridge);
        Router router = new UpdateRouter(handler, parser, pipeline);

        BotConfig config = new BotConfig(
                token,
                name,
                parser,
                client,
                sessions,
                router
        );
        CrocodileBot bot = new CrocodileBot(config);
        client.setBot(bot);

        return bot;
    }
}
