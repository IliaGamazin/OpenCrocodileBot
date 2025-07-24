package commands.controllers.proxies;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.middleware.ThrowingConsumer;

public interface ControllerProxy {
    Controller wrap(ThrowingConsumer<AuthedConfig, Exception> logic);
}
