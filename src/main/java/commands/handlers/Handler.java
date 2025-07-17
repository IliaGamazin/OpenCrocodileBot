package commands.handlers;

import commands.controllers.Controller;

import java.util.Optional;

public interface Handler {
    Optional<Controller> get(String key);
}
