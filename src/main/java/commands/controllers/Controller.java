package commands.controllers;

import bot.config.AuthedConfig;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Controller {
    void handle(AuthedConfig config) throws TelegramApiException;
}
