package app.model.sessions;

import app.utilities.Language;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class SessionHandler {
    private final SessionRepository sessions;

    public SessionHandler(SessionRepository sessions) {
        this.sessions = sessions;
    }

    public Session getOrCreate(long chat) {
        return sessions.findById(chat).orElseGet(() -> sessions.save(new Session(chat, Language.ENGLISH)));
    }

    public Optional<Session> getSession(long chat) {
        return sessions.findById(chat);
    }

    public void changeLanguage(long chat, Language language) {
        getSession(chat).ifPresent(session -> {
                session.setLanguage(language);
                sessions.save(session);
            }
        );
    }
}
