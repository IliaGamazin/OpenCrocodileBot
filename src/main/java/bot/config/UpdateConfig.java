package bot.config;

import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;

import java.util.Optional;

public class UpdateConfig {
    private final Update update;
    private final String[] args;
    private Session session;

    public UpdateConfig(Update update, String[] args) {
        this.update = update;
        this.args = args;
    }

    public Optional<Session> getSession() {
        return Optional.ofNullable(session);
    }

    public Update getUpdate() {
        return update;
    }

    public String[] getArgs() {
        return args;
    }
}
