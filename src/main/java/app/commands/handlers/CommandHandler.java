package app.commands.handlers;

import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.commands.controllers.proxies.ExceptionProxy;
import jakarta.ws.rs.ConstrainedTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.naming.ldap.Control;
import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements CommandRepo {
    private final HashMap<String, Controller> commands;
    private final Controller base;

    public CommandHandler(Controller base) {
        commands = new HashMap<>();
        this.base = base;
    }

    @Override
    public void register(String command, Controller controller) {
        commands.put(command, controller);
    }

    @Override
    public Controller getOrDefault(String command) {
        return commands.getOrDefault(command, base);
    }
}
