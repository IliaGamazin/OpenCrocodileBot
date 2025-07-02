package handlers;

import controllers.Controller;
import controllers.commands.LanguageController;
import controllers.commands.MessageController;
import controllers.commands.RunController;
import services.client.TelegramClient;
import services.sessions.SessionHandler;

import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements Handler{
    private final HashMap<String, Controller> commands;

    public CommandHandler(SessionHandler sessions, TelegramClient client) {
        commands = new HashMap<>();
        commands.put("run", new RunController(sessions, client));
        commands.put("language", new LanguageController(sessions, client));
        commands.put("message", new MessageController(sessions));
    }

    public Optional<Controller> get(String callback) {
        return Optional.ofNullable(commands.get(callback));
    }
}
