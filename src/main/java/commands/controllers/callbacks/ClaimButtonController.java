package commands.controllers.callbacks;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import game.GameHandler;


public class ClaimButtonController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;

    public ClaimButtonController(GameHandler games, TelegramClient client) {
        this.games = games;
        this.client = client;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {

    }
}
