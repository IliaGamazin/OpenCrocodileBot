package app.authentication.client;

import app.commands.middleware.ThrowingConsumer;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public interface TelegramClient {
    void setExecute(ThrowingConsumer<BotApiMethod<? extends Serializable>, TelegramApiException> execute);
    <T extends Serializable> void execute(BotApiMethod<T> method) throws TelegramApiException;
}
