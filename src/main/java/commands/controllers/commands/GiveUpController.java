package commands.controllers.commands;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import game.GameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import services.messages.MessageDirector;

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

            games.end(chat);

            MessageDirector director = new MessageDirector();
            SendMessage message = director.constructLoseMessage(chat, from.getFirstName(), from.getId());
            client.execute(message);
        }).handle(config);
    }
}
