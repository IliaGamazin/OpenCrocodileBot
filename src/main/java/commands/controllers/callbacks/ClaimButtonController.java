package commands.controllers.callbacks;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import game.GameHandler;
import org.telegram.telegrambots.meta.api.objects.Update;


public class ClaimButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;

    public ClaimButtonController(TelegramClient client, GameHandler games) {
        this.games = games;
        this.client = client;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        Update update = config.update();
    }
}
