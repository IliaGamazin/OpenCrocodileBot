package commands.middleware;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;

@FunctionalInterface
public interface Middleware<T> {
    void handle(T config, Consumer<T> next) throws TelegramApiException;
}
