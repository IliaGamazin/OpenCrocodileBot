package authentication;

import authentication.sessions.Session;
import bot.config.UnAuthedConfig;
import authentication.sessions.SessionHandler;

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
