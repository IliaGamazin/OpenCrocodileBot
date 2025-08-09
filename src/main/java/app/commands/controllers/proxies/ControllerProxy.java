package app.commands.controllers.proxies;

import app.commands.dto.AuthedConfig;
import app.commands.controllers.Controller;
import app.commands.middleware.ThrowingConsumer;

public interface ControllerProxy {
    Controller wrap(ThrowingConsumer<AuthedConfig, Exception> logic);
}
