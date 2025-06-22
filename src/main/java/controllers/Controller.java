package controllers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Controller {
    void handle(Update update);
}
