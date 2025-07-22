package commands.controllers;

import bot.config.AuthedConfig;
import commands.middleware.ThrowingConsumer;

public interface ControllerProxy {
    Controller wrap(ThrowingConsumer<AuthedConfig, Exception> logic);
}
