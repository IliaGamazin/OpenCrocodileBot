package app.authentication;

import app.model.sessions.Session;
import app.model.sessions.SessionHandler;
import app.commands.dto.UnAuthedDTO;
import jakarta.transaction.Transactional;

@Transactional
public class Authenticator implements AuthBridge{
    private final SessionHandler sessions;

    public Authenticator(SessionHandler sessions) {
        this.sessions = sessions;
    }

    @Override
    public Session authenticate(UnAuthedDTO config) {
        return sessions.getOrCreate(config.chat());
    }
}
