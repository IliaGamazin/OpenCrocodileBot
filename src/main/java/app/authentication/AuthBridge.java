package app.authentication;

import app.model.sessions.Session;
import app.commands.dto.UnAuthedDTO;

@FunctionalInterface
public interface AuthBridge {
    Session authenticate(UnAuthedDTO config);
}
