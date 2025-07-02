package bot;

import handlers.CallbackHandler;
import handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import routers.CallbackRouter;
import routers.MessageRouter;
import services.client.TelegramClient;
import services.parsers.Parser;
import services.parsers.UniversalParser;
import services.sessions.SessionHandler;


public class CrocodileBot extends TelegramLongPollingBot {
    private final MessageRouter messageRouter;
    private final CallbackRouter callbackRouter;

    public CrocodileBot(String token, TelegramClient client) {
        super(token);
        SessionHandler sessions = new SessionHandler();

        CommandHandler commands = new CommandHandler(sessions, client);
        CallbackHandler callbacks = new CallbackHandler(sessions, client);

        Parser parser = new UniversalParser(this.getBotUsername());

        messageRouter = new MessageRouter(commands, parser);
        callbackRouter = new CallbackRouter(callbacks, parser);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            messageRouter.route(update);
        }
        else if (update.hasCallbackQuery()) {
            callbackRouter.route(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "OpenCrocodileBot";
    }
}
