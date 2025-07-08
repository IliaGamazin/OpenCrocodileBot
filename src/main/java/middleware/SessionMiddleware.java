package middleware;

import bot.config.UpdateConfig;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;
import services.sessions.SessionHandler;

import java.util.function.Consumer;

public class SessionMiddleware implements Middleware{
    private final SessionHandler sessions;

    public SessionMiddleware(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public void handle(UpdateConfig config, Consumer<UpdateConfig> next) {
        Update update = config.update();

        long chat = update.hasMessage() ?
                update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();
        Session session = sessions.getOrCreate(chat);

        config = config.withSession(session);
        next.accept(config);
    }
}
