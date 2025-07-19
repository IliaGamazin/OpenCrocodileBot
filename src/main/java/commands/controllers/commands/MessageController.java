package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;

public class MessageController implements Controller {
    @Override
    public void handle(AuthedConfig config) {
        System.out.println("message");
    }
}
