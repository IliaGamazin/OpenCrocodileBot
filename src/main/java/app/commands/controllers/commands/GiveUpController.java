package app.commands.controllers.commands;

import app.authentication.client.TelegramClient;
import app.commands.dto.AuthedConfig;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import app.exceptions.ValidationException;
import app.game.GameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import app.services.messages.MessageDirector;

public class GiveUpController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public GiveUpController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.games = games;
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            User from = update.getMessage().getFrom();
            long chat = config.chat();

            if (games.get(chat).isEmpty()) {
                throw new ValidationException("Game is inactive!");
            }

            games.end(chat);

            MessageDirector director = new MessageDirector();
            SendMessage message = director.constructLoseMessage(chat, from.getFirstName(), from.getId());
            client.execute(message);
        }).handle(config);
    }
}
