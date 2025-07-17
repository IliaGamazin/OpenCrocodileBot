package authentication.sessions;

import utilities.Language;
import java.util.HashMap;
import java.util.Optional;

public class SessionHandler {
    private final HashMap<Long, Session> sessions;

    public SessionHandler() {
        sessions = new HashMap<>();
    }

    public Session getOrCreate(long chatId) {
        Session session = exists(chatId) ? sessions.get(chatId) : new Session(chatId, Language.ENGLISH);
        sessions.put(chatId, session);
        return session;
    }

    public boolean exists(long chatId) {
        return sessions.containsKey(chatId);
    }

    public Optional<Session> getSession(long chatId) {
        return Optional.ofNullable(sessions.get(chatId));
    }

    public void changeLanguage(long chatId, Language language) {
        getSession(chatId).ifPresent(session ->
                sessions.put(chatId, session.withLanguage(language))
        );
    }
}
