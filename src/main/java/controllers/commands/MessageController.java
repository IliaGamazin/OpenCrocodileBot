package controllers.commands;

import bot.config.UpdateConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.SessionHandler;

public class MessageController implements Controller {
    private final SessionHandler sessions;

    public MessageController(SessionHandler sessions) {
        this.sessions = sessions;
    }
    
    @Override
    public void handle(UpdateConfig config) throws TelegramApiException {
        System.out.println("message");
    }
}
