package controllers.commands;

import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.randomword.WordProvider;
import services.randomword.wiktionary.WiktionaryProvider;
import services.sender.Sender;
import services.sessions.SessionHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class RunController implements Controller {
    private final SessionHandler sessions;
    private final Sender sender;

    public RunController(SessionHandler sessions, Sender sender) {
        this.sessions = sessions;
        this.sender = sender;
    }

    @Override
    public void handle(Update update) {
        long chat = update.getMessage().getChatId();
        if (!sessions.exists(chat)) {
            sessions.addSession(chat);
            System.out.println("Session created for " + chat);
        }
        else {
            System.out.println("Session already exists for " + chat);
        }

        WordProvider provider = new WiktionaryProvider(sessions.getSession(chat).getLanguage());
        String word = "";
        try {
             word = provider.getRandomWord();
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        word = word.replace("_", " ");

        SendMessage message = new SendMessage();
        message.setText(word);
        message.setChatId(chat);

        sender.send(message);
    }
}
