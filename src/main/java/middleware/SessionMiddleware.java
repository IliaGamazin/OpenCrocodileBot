package middleware;

import bot.config.UnAuthedConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.Session;
import services.sessions.SessionHandler;

import java.util.function.Consumer;

public class SessionMiddleware implements AuthBridge{
    private final SessionHandler sessions;

    public SessionMiddleware(SessionHandler sessions) {
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
