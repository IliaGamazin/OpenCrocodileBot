package controllers.commands;

import bot.config.UpdateConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.randomword.WordProvider;
import services.randomword.wiktionary.WiktionaryProvider;
import services.sessions.SessionHandler;

import java.io.IOException;
import java.net.URISyntaxException;

public class RunController implements Controller {
    private final SessionHandler sessions;
    private final TelegramClient client;

    public RunController(SessionHandler sessions, TelegramClient client) {
        this.sessions = sessions;
        this.client = client;
    }

    @Override
    public void handle(UpdateConfig config) throws TelegramApiException {
        Update update = config.getUpdate();
        String[] arguments = config.getArgs();

        long chat = update.getMessage().getChatId();

        WordProvider provider = new WiktionaryProvider(sessions.getSession(chat).get().getLanguage());
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

        client.execute(message);
    }
}
