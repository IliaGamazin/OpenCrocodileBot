package authentication.client;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public class TelegramBotClient implements TelegramClient {
    private TelegramLongPollingBot bot;

    @Override
    public void setBot(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public <T extends Serializable> void execute(BotApiMethod<T> method) throws TelegramApiException {
        if (bot == null) {
            throw new IllegalStateException("Bot not initialized. Call setBot() first.");
        }
        bot.execute(method);
    }
}
