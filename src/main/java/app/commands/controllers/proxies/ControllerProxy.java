package app.commands.controllers.proxies;

import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.middleware.ThrowingConsumer;

public interface ControllerProxy {
    Controller wrap(ThrowingConsumer<AuthedDTO, Exception> logic);
}
