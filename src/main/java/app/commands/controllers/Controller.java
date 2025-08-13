package app.commands.controllers;

import app.commands.dto.AuthedDTO;
import app.exceptions.ControllerException;

public interface Controller {
    void handle(AuthedDTO config) throws ControllerException;
}
