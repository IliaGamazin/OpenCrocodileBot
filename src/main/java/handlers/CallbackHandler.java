package handlers;

import controllers.Controller;
import services.sender.Sender;
import services.sessions.SessionHandler;

import java.util.HashMap;
import java.util.Optional;

public class CallbackHandler implements Handler{
    private final HashMap<String, Controller> callbacks;

    public CallbackHandler(SessionHandler sessions, Sender sender) {
        callbacks = new HashMap<>();
    }

    public Optional<Controller> get(String callback) {
        return Optional.ofNullable(callbacks.get(callback));
    }
}
