package middleware;

import bot.config.UnAuthedConfig;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.Session;

@FunctionalInterface
public interface AuthBridge {
    Session authenticate(UnAuthedConfig config) throws TelegramApiException;
}
