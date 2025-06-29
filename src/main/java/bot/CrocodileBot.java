package bot;

import handlers.CallbackHandler;
import handlers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import routers.CallbackRouter;
import routers.MessageRouter;
import services.sender.Sender;
import services.sessions.SessionHandler;


public class CrocodileBot extends TelegramLongPollingBot implements Sender {
    private final MessageRouter messageRouter;
    private final CallbackRouter callbackRouter;

    public CrocodileBot(String token) {
        super(token);
        SessionHandler sessions = new SessionHandler();

        CommandHandler commands = new CommandHandler(sessions, this);
        CallbackHandler callbacks = new CallbackHandler(sessions, this);

        messageRouter = new MessageRouter(commands);
        callbackRouter = new CallbackRouter(callbacks);
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
        return "CrocodileBot";
    }

    @Override
    public void send(SendMessage message) {
        try {
            execute(message);
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
