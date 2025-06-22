package bot;

import controllers.CommandHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import router.MessageRouter;
import services.sender.Sender;
import services.sessions.SessionHandler;


public class CrocodileBot extends TelegramLongPollingBot implements Sender {
    private final MessageRouter router;
    public CrocodileBot(String token) {
        super(token);
        SessionHandler sessions = new SessionHandler();
        CommandHandler commands = new CommandHandler(sessions, this);

        router = new MessageRouter(commands);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            router.route(update);
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
