package controllers.commands;

import bot.config.UnAuthedUpdate;
import controllers.Controller;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.SessionHandler;

public class MessageController implements Controller {
    @Override
    public void handle(UnAuthedUpdate config) throws TelegramApiException {
        System.out.println("message");
    }
}
