package commands.handlers;

import commands.controllers.Controller;

import java.util.Optional;

public interface CommandRepo {
    Optional<Controller> get(String key);
    void register(String command, Controller controller);
}
