package controllers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Controller {
    void handle(Update update, String[] arguments) throws TelegramApiException;
}
