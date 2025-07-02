package handlers;

import controllers.Controller;
import controllers.callbacks.LanguageButtonController;
import services.client.TelegramClient;
import services.sessions.SessionHandler;

import java.util.HashMap;
import java.util.Optional;

public class CallbackHandler implements Handler{
    private final HashMap<String, Controller> callbacks;

    public CallbackHandler(SessionHandler sessions, TelegramClient client) {
        callbacks = new HashMap<>();
        callbacks.put("language", new LanguageButtonController(sessions, client));
    }

    public Optional<Controller> get(String callback) {
        return Optional.ofNullable(callbacks.get(callback));
    }
}
