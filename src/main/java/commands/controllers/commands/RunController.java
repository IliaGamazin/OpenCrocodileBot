package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import exceptions.ValidationException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import authentication.client.TelegramClient;
import game.GameHandler;
import services.messages.MessageDirector;

public class RunController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public RunController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.games = games;
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            long chat = config.chat();
            long master = update.getMessage().getFrom().getId();

            MessageDirector director = new MessageDirector();

            if (games.get(chat).isPresent()) {
                throw new ValidationException("Game is already started!");
            }
            games.start(chat, master, config.session().language());

            SendMessage message = director.constructWordMessage(chat, update.getMessage().getFrom().getFirstName());
            client.execute(message);
        }).handle(config);
    }
}
