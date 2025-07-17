package authentication;

import authentication.sessions.Session;
import bot.config.UnAuthedConfig;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@FunctionalInterface
public interface AuthBridge {
    Session authenticate(UnAuthedConfig config) throws TelegramApiException;
}
