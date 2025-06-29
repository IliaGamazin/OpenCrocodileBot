package handlers;

import controllers.Controller;
import services.sender.Sender;
import services.sessions.SessionHandler;

import java.util.HashMap;

public class CallbackHandler {
    private final HashMap<String, Controller> callbacks;

    public CallbackHandler(SessionHandler sessions, Sender sender) {
        callbacks = new HashMap<>();
    }

    public Controller get(String callback) {
        return callbacks.get(callback);
    }
}
