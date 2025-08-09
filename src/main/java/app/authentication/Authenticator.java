package app.authentication;

import app.authentication.sessions.Session;
import app.authentication.sessions.SessionHandler;
import app.commands.dto.UnAuthedConfig;

public class Authenticator implements AuthBridge{
    private final SessionHandler sessions;

    public Authenticator(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public Session authenticate(UnAuthedConfig config) {
        return sessions.getOrCreate(config.chat());
    }
}
