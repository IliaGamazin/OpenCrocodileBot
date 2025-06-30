package controllers.commands;

import controllers.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.SessionHandler;

public class MessageController implements Controller {
    private final SessionHandler sessions;

    public MessageController(SessionHandler sessions) {
        this.sessions = sessions;
    }
    
    @Override
    public void handle(Update update) {
        System.out.println("message");
        sessions.getAll();
    }
}
