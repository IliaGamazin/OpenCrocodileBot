package controllers.commands;

import bot.config.UpdateConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;
import services.sessions.Session;
import services.sessions.SessionHandler;

import java.util.Optional;

public class LanguageController implements Controller {
    private final SessionHandler sessions;
    private final TelegramClient client;

    public LanguageController(SessionHandler sessions, TelegramClient client) {
        this.sessions = sessions;
        this.client = client;
    }

    @Override
    public void handle(UpdateConfig config) throws TelegramApiException {
        Update update = config.getUpdate();

        MessageDirector director = new MessageDirector();
        Builder builder = new MessageBuilder();
        long chat = update.getMessage().getChatId();

        Optional<Session> session = sessions.getSession(chat);
        if (session.isPresent()) {
            director.constructLanguageMessage(builder, session.get());
            SendMessage message = builder.build();
            client.execute(message);
        }
    }
}
