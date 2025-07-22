package authentication;

import authentication.sessions.Session;
import bot.config.UnAuthedConfig;

@FunctionalInterface
public interface AuthBridge {
    Session authenticate(UnAuthedConfig config);
}
