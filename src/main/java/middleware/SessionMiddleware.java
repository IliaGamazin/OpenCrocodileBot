package middleware;

import bot.config.UpdateConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.SessionHandler;

import java.util.function.Consumer;

public class SessionMiddleware implements Middleware{
    private final SessionHandler sessions;

    public SessionMiddleware(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public void handle(UpdateConfig config, Consumer<UpdateConfig> next) {
        Update update = config.getUpdate();

        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        if (!sessions.exists(chat)) {
            sessions.addSession(chat);
        }

        next.accept(config);
    }
}
