package bot;

import bot.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CrocodileBot extends TelegramLongPollingBot {
    private final BotConfig config;

    public CrocodileBot(BotConfig config) {
        super(config.token());
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        config.router().route(update);
    }

    @Override
    public String getBotUsername() {
        return config.name();
    }
}
