package app.authentication;

import app.authentication.sessions.Session;
import app.commands.dto.UnAuthedConfig;

@FunctionalInterface
public interface AuthBridge {
    Session authenticate(UnAuthedConfig config);
}
