package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import exceptions.GameException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import authentication.client.TelegramClient;
import game.GameHandler;
import game.GameState;
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
    public void handle(AuthedConfig config) throws ControllerException {
        try {
            Update update = config.update();
            long chat = config.chat();
            long master = update.getMessage().getFrom().getId();

            MessageDirector director = new MessageDirector();
            Builder builder = new MessageBuilder();

            GameState game = games.start(chat, master, config.session().language());
            director.constructWordMessage(builder, config.session(), game);
            SendMessage message = builder.build();
            client.execute(message);
        }
        catch (TelegramApiException | IOException | URISyntaxException e ) {
            throw new GameException("Service failed", e);
        }
    }
}
