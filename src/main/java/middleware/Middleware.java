package middleware;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;

@FunctionalInterface
public interface Middleware {
    void handle(Update update, Consumer<Update> next) throws TelegramApiException;
}
