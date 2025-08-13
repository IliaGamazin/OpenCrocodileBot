package app.commands.controllers.commands;

import app.authentication.client.TelegramClient;
import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import app.model.games.Game;
import app.model.games.GameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import app.services.messages.MessageDirector;

import java.util.Optional;

public class MessageController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public MessageController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.games = games;
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedDTO config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            User from = update.getMessage().getFrom();
            long chat = config.chat();

            Optional<Game> gameOpt = games.get(config.chat());
            if (gameOpt.isEmpty()) {
                return;
            }

            Game game = gameOpt.get();

            if (game.getWord().equalsIgnoreCase(config.update().getMessage().getText())
                && from.getId() != game.getMaster()) {
                games.end(chat);

                MessageDirector director = new MessageDirector();
                SendMessage message = director.constructWinMessage(chat, from.getFirstName(), from.getId());
                client.execute(message);
            }
        }).handle(config);
    }
}
