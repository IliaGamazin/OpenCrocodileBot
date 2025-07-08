package controllers;

import bot.config.UpdateConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Controller {
    void handle(UpdateConfig config) throws TelegramApiException;
}
