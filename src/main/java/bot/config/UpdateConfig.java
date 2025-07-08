package bot.config;

import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;

public record UpdateConfig(Update update, String[] args, Session session) {
    public UpdateConfig withSession(Session session) {
        return new UpdateConfig(this.update, this.args, session);
    }
    public Session session() {
        if (session == null) {
            throw new IllegalStateException("Session not initialized - check middleware configuration");
        }
        return session;
    }
}
