package app.commands.handlers;

import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.commands.controllers.proxies.ExceptionProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements CommandRepo {
    private final HashMap<String, Controller> commands;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    @Override
    public void register(String command, Controller controller) {
        commands.put(command, controller);
    }

    @Override
    public Optional<Controller> get(String command) {
        return Optional.ofNullable(commands.get(command));
    }
}
