package commands.handlers;

import commands.controllers.Controller;
import commands.controllers.callbacks.LanguageButtonController;
import commands.controllers.callbacks.NextButtonController;
import commands.controllers.callbacks.SeeButtonController;
import commands.controllers.commands.LanguageController;
import commands.controllers.commands.MessageController;
import commands.controllers.commands.RunController;
import authentication.client.TelegramClient;
import game.GameHandler;
import authentication.sessions.SessionHandler;

import java.util.HashMap;
import java.util.Optional;

public class CommandHandler implements Handler{
    private final HashMap<String, Controller> commands;

    public CommandHandler(SessionHandler sessions, GameHandler games, TelegramClient client) {
        commands = new HashMap<>();
        commands.put("run", new RunController(games, client));
        commands.put("language", new LanguageController(client));
        commands.put("message", new MessageController());
        commands.put("language-callback", new LanguageButtonController(sessions, client));
        commands.put("see-callback", new SeeButtonController(client, games));
        commands.put("next-callback", new NextButtonController(client, games));
    }

    public Optional<Controller> get(String command) {
        return Optional.ofNullable(commands.get(command));
    }
}
