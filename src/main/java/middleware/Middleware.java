package middleware;

import bot.config.UpdateConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;

@FunctionalInterface
public interface Middleware {
    void handle(UpdateConfig config, Consumer<UpdateConfig> next) throws TelegramApiException;
}
