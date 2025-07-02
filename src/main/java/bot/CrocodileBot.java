package bot;

import handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import routers.UpdateRouter;
import services.client.TelegramClient;
import services.parsers.Parser;
import services.parsers.UniversalParser;
import services.sessions.SessionHandler;

public class CrocodileBot extends TelegramLongPollingBot {
    private final UpdateRouter router;

    public CrocodileBot(String token, TelegramClient client) {
        super(token);

        SessionHandler sessions = new SessionHandler();
        CommandHandler commands = new CommandHandler(sessions, client);

        Parser parser = new UniversalParser(this.getBotUsername());
        router = new UpdateRouter(commands, parser);
    }

    @Override
    public void onUpdateReceived(Update update) {
        router.route(update);
    }

    @Override
    public String getBotUsername() {
        return "OpenCrocodileBot";
    }
}
