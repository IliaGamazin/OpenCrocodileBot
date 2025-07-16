package controllers.commands;

import bot.config.AuthedConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageController implements Controller {
    @Override
    public void handle(AuthedConfig config) throws TelegramApiException {
        System.out.println("message");
    }
}
