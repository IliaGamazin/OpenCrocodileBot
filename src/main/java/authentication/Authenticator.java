package authentication;

import authentication.sessions.Session;
import bot.config.UnAuthedConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import authentication.sessions.SessionHandler;

public class Authenticator implements AuthBridge{
    private final SessionHandler sessions;

    public Authenticator(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public Session authenticate(UnAuthedConfig config) throws TelegramApiException {
        Update update = config.update();
        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        return sessions.getOrCreate(chat);
    }
}
