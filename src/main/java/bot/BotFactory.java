package bot;

import bot.config.BotConfig;
import middleware.LoggerMiddleware;
import middleware.Middleware;
import middleware.SessionMiddleware;
import services.client.TelegramBotClient;
import handlers.CommandHandler;
import handlers.Handler;
import routers.Router;
import routers.UpdateRouter;
import services.client.TelegramClient;
import services.game.GameHandler;
import services.parsers.Parser;
import services.parsers.UniversalParser;
import services.randomword.WordProvider;
import services.randomword.wiktionary.WiktionaryProvider;
import services.sessions.SessionHandler;

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
        List<Middleware> middlewares = new ArrayList<>();
        middlewares.add(new SessionMiddleware(sessions));
        middlewares.add(new LoggerMiddleware());

        Router router = new UpdateRouter(handler, parser, middlewares);

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
