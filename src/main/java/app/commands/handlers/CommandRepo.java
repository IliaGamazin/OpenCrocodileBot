package app.commands.handlers;

import app.commands.controllers.Controller;

import java.util.Optional;

public interface CommandRepo {
    Controller getOrDefault(String key);
    void register(String command, Controller controller);
}
