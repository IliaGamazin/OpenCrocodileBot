package handlers;

import controllers.Controller;
import controllers.callbacks.LanguageButtonController;
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
        commands.put("language-callback", new LanguageButtonController(sessions, client));
    }

    public Optional<Controller> get(String command) {
        return Optional.ofNullable(commands.get(command));
    }
}
