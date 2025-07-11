package controllers;

import bot.config.UnAuthedUpdate;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Controller {
    void handle(UnAuthedUpdate config) throws TelegramApiException;
}
