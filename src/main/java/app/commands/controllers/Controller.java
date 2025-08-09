package app.commands.controllers;

import app.commands.dto.AuthedConfig;
import app.exceptions.ControllerException;

public interface Controller {
    void handle(AuthedConfig config) throws ControllerException;
}
