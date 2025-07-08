package middleware;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.Consumer;

public class LoggerMiddleware implements Middleware{
    @Override
    public void handle(Update update, Consumer<Update> next) throws TelegramApiException {
        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        User from = update.hasMessage() ?
                update.getMessage().getFrom() :
                update.getCallbackQuery().getFrom();

        System.out.println("Chat: " + chat);
        System.out.println("User: " + from);

        next.accept(update);
    }
}
