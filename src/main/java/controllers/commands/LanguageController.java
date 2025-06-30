package controllers.commands;

import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;
import services.sender.Sender;
import services.sessions.Session;
import services.sessions.SessionHandler;

public class LanguageController implements Controller {
    private final SessionHandler sessions;
    private final Sender sender;


    public LanguageController(SessionHandler sessions, Sender sender) {
        this.sessions = sessions;
        this.sender = sender;
    }

    @Override
    public void handle(Update update) {
        MessageDirector director = new MessageDirector();
        Builder builder = new MessageBuilder();
        long chat = update.getMessage().getChatId();

        if (sessions.exists(chat)) {
            Session session = sessions.getSession(chat);
            director.constructLanguageMessage(builder, session);
            SendMessage message = builder.build();
            sender.send(message);
        }
    }
}
