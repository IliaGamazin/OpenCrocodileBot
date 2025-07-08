package controllers.commands;

import bot.config.UpdateConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.game.GameHandler;
import services.game.GameState;
import services.sessions.Session;
import services.sessions.SessionHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class RunController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;

    public RunController(GameHandler games, TelegramClient client) {
        this.games = games;
        this.client = client;
    }

    @Override
    public void handle(UpdateConfig config) throws TelegramApiException {
        Update update = config.update();
        long chat = update.getMessage().getChatId();
        long master = update.getMessage().getFrom().getId();

        try {
            GameState game = games.start(chat, master, config.session().language());
            String word = game.word().replace("_", " ");
            SendMessage message = new SendMessage();
            message.setText(word);
            message.setChatId(chat);
            client.execute(message);
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
