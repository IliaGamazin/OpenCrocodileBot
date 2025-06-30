package handlers;

import controllers.Controller;
import controllers.commands.LanguageController;
import controllers.commands.MessageController;
import controllers.commands.RunController;
import services.sender.Sender;
import services.sessions.SessionHandler;

import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements Handler{
    private final HashMap<String, Controller> commands;

    public CommandHandler(SessionHandler sessions, Sender sender) {
        commands = new HashMap<>();
        commands.put("/run@OpenCrocodileBot", new RunController(sessions, sender));
        commands.put("/language@OpenCrocodileBot", new LanguageController(sessions, sender));
        commands.put("/message", new MessageController(sessions));
    }

    public Optional<Controller> get(String callback) {
        return Optional.ofNullable(commands.get(callback));
    }
}
