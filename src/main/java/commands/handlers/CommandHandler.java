package commands.handlers;

import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import commands.controllers.proxies.ExceptionProxy;

import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements CommandRepo {
    private final HashMap<String, Controller> commands;

    public CommandHandler() {
        ControllerProxy proxy = new ExceptionProxy();
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
