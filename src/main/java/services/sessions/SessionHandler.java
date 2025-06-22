package services.sessions;

import utilities.Language;

import java.util.HashMap;
import java.util.Map;

public class SessionHandler {
    private final HashMap<Long, Session> sessions;

    public SessionHandler() {
        sessions = new HashMap<>();
    }

    public void addSession(long chatId) {
        sessions.put(chatId, new Session(chatId, Language.ENGLISH));
    }

    public boolean exists(long chatId) {
        return sessions.containsKey(chatId);
    }

    public void removeSession(long chatId) {
        sessions.remove(chatId);
    }

    public Session getSession(long chatId) {
        return sessions.get(chatId);
    }

    public void getAll() {
        for (Map.Entry<Long, Session> entry : sessions.entrySet()) {
            System.out.println(entry.getValue().getChatId());
        }
    }
}
