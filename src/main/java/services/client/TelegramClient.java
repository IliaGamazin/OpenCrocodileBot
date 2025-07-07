package services.client;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public interface TelegramClient {
    public void setBot(TelegramLongPollingBot bot);
    <T extends Serializable> void execute(BotApiMethod<T> method) throws TelegramApiException;
}
