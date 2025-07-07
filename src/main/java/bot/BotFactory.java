package bot;

import bot.config.BotConfig;
import bot.config.TelegramBotClient;
import handlers.CommandHandler;
import handlers.Handler;
import routers.Router;
import routers.UpdateRouter;
import services.client.TelegramClient;
import services.parsers.Parser;
import services.parsers.UniversalParser;
import services.sessions.SessionHandler;

import java.sql.Struct;

public class BotFactory {
    public BotFactory() {}

    public CrocodileBot createBot(String token, String name) {
        TelegramClient client = new TelegramBotClient();
        SessionHandler sessions = new SessionHandler();

        Parser parser = new UniversalParser(name);
        Handler handler = new CommandHandler(sessions, client);
        Router router = new UpdateRouter(handler, parser);

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
