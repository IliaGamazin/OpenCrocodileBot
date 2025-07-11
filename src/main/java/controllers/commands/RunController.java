package controllers.commands;

import bot.config.UnAuthedUpdate;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.game.GameHandler;
import services.game.GameState;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;

import java.io.IOException;
import java.net.URISyntaxException;

public class RunController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;

    public RunController(GameHandler games, TelegramClient client) {
        this.games = games;
        this.client = client;
    }

    @Override
    public void handle(UnAuthedUpdate config) throws TelegramApiException {
        Update update = config.update();
        long chat = config.chat();
        long master = update.getMessage().getFrom().getId();

        MessageDirector director = new MessageDirector();
        Builder builder = new MessageBuilder();

        try {
            GameState game = games.start(chat, master, config.session().language());
            director.constructWordMessage(builder, config.session(), game);
            SendMessage message = builder.build();
            client.execute(message);
        }
        catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
