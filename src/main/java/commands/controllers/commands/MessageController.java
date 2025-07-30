package commands.controllers.commands;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import game.GameHandler;
import game.GameState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import services.messages.MessageDirector;

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
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            User from = update.getMessage().getFrom();
            long chat = config.chat();

            Optional<GameState> gameOpt = games.get(config.chat());
            if (gameOpt.isEmpty()) {
                return;
            }

            GameState game = gameOpt.get();

            if (game.word().equalsIgnoreCase(config.update().getMessage().getText())
                && from.getId() != game.master()) {
                games.end(chat);

                MessageDirector director = new MessageDirector();
                SendMessage message = director.constructWinMessage(chat, from.getFirstName(), from.getId());
                client.execute(message);
            }
        }).handle(config);
    }
}
