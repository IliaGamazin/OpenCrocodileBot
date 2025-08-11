package app.commands.middleware;

import app.commands.dto.UnAuthedConfig;
import app.exceptions.PipelineException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class LoggerMiddleware implements Middleware<UnAuthedConfig, PipelineException>{
    @Override
    public void handle(UnAuthedConfig config, ThrowingConsumer<UnAuthedConfig, PipelineException> next) throws PipelineException {
        Update update = config.update();

        long chat = config.chat();
        User from = update.hasMessage() ?
                update.getMessage().getFrom() :
                update.getCallbackQuery().getFrom();

        System.out.println("Chat: " + chat);
        System.out.println("User: " + from);

        next.accept(config);
    }
}
