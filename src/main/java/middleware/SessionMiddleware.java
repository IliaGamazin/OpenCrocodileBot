package middleware;

import bot.config.UnAuthedUpdate;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;
import services.sessions.SessionHandler;

import java.util.function.Consumer;

public class SessionMiddleware implements Middleware<UnAuthedUpdate>{
    private final SessionHandler sessions;

    public SessionMiddleware(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public void handle(UnAuthedUpdate config, Consumer<UnAuthedUpdate> next) {
        Update update = config.update();

        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        Session session = sessions.getOrCreate(chat);

        config = config.withSession(session);
        next.accept(config);
    }
}
