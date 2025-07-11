package middleware;

import bot.config.UnAuthedUpdate;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.function.Consumer;

public class LoggerMiddleware implements Middleware<UnAuthedUpdate>{
    @Override
    public void handle(UnAuthedUpdate config, Consumer<UnAuthedUpdate> next) {
        Update update = config.update();

        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        User from = update.hasMessage() ?
                update.getMessage().getFrom() :
                update.getCallbackQuery().getFrom();

        System.out.println("Chat: " + chat);
        System.out.println("User: " + from);

        next.accept(config);
    }
}
