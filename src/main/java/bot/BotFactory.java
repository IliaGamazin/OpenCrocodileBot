package bot;

import authentication.AuthBridge;
import authentication.Authenticator;
import bot.config.AuthedConfig;
import bot.config.BotConfig;
import bot.config.UnAuthedConfig;
import commands.controllers.callbacks.ClaimButtonController;
import commands.controllers.callbacks.LanguageButtonController;
import commands.controllers.callbacks.NextButtonController;
import commands.controllers.callbacks.SeeButtonController;
import commands.controllers.commands.GiveUpController;
import commands.controllers.commands.LanguageController;
import commands.controllers.commands.MessageController;
import commands.controllers.commands.RunController;
import commands.controllers.proxies.ControllerProxy;
import commands.controllers.proxies.ExceptionProxy;
import commands.middleware.*;
import authentication.client.TelegramBotClient;
import commands.handlers.CommandHandler;
import commands.handlers.CommandRepo;
import exceptions.PipelineException;
import jdk.javadoc.doclet.Reporter;
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

        CommandRepo handler = getRepo(client, games, sessions);
        Parser parser = new UniversalParser(name);

        Pipeline pipeline = getPipeline(client, sessions);

        Router router = new UpdateRouter(handler, parser, pipeline);

        BotConfig config = new BotConfig(
                token,
                name,
                router
        );
        CrocodileBot bot = new CrocodileBot(config);
        client.setBot(bot);

        return bot;
    }

    private Pipeline getPipeline(TelegramClient client, SessionHandler sessions) {
        List<Middleware<UnAuthedConfig, PipelineException>> preAuthMiddlewares = List.of(new ErrorHandler(client), new LoggerMiddleware());
        List<Middleware<AuthedConfig, PipelineException>> postAuthMiddlewares = new ArrayList<>();

        MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain = new MiddlewareChain<>(preAuthMiddlewares);
        MiddlewareChain<AuthedConfig, PipelineException> postAuthChain = new MiddlewareChain<>(postAuthMiddlewares);

        AuthBridge bridge = new Authenticator(sessions);

        return new Pipeline(preAuthChain, postAuthChain, bridge);
    }

    private CommandRepo getRepo(TelegramClient client, GameHandler games, SessionHandler sessions) {
        ControllerProxy proxy = new ExceptionProxy();
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
}
