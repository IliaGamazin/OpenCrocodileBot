package app.authentication.client;

import app.commands.middleware.ThrowingConsumer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Component
public class TelegramBotClient implements TelegramClient {
    private ThrowingConsumer<BotApiMethod<? extends Serializable>, TelegramApiException> execute;

    public void setExecute(ThrowingConsumer<BotApiMethod<? extends Serializable>, TelegramApiException> execute) {
        this.execute = execute;
    }

    @Override
    public <T extends Serializable> void execute(BotApiMethod<T> method) throws TelegramApiException {
        execute.accept(method);
    }
}
