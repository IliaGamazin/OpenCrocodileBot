package commands.controllers;

import bot.config.AuthedConfig;
import exceptions.ControllerException;

public interface Controller {
    void handle(AuthedConfig config) throws ControllerException;
}
