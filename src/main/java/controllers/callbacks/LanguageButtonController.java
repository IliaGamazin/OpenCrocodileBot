package controllers.callbacks;

import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;
import services.sender.Sender;
import services.sessions.Session;
import services.sessions.SessionHandler;
import utilities.Language;

import java.util.Optional;

public class LanguageButtonController implements Controller {
    private final SessionHandler sessions;
    private final Sender sender;

    public LanguageButtonController(SessionHandler sessions, Sender sender) {
        this.sessions = sessions;
        this.sender = sender;
    }

    @Override
    public void handle(Update update, String[] arguments) {
        long chat = update.getCallbackQuery().getMessage().getChatId();
        String query = update.getCallbackQuery().getId();
        String code = arguments[0];

        sessions.changeLanguage(chat, Language.fromCode(code));

        AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text("Settings changed successfuly")
                .showAlert(false)
                .build();

        sender.send(answer);

        MessageDirector director = new MessageDirector();
        Builder builder = new MessageBuilder();
        Optional<Session> session = sessions.getSession(chat);

        if (session.isPresent()) {
            director.constructLanguageChangedMessage(builder, session.get());
            SendMessage message = builder.build();
            sender.send(message);
        }
    }
}
