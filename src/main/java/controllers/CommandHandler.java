package controllers;

import services.sender.Sender;
import services.sessions.SessionHandler;

import java.util.HashMap;

public class CommandHandler {
    private final HashMap<String, Controller> commands;

    public CommandHandler(SessionHandler sessions, Sender sender) {
        commands = new HashMap<>();
        commands.put("/run@OpenCrocodileBot", new RunController(sessions, sender));
        commands.put("/message", new MessageController(sessions));
    }

    public Controller get(String command) {
        return commands.getOrDefault(command, commands.get("/message"));
    }
}
