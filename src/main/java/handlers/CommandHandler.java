package handlers;

import controllers.Controller;
import controllers.callbacks.LanguageButtonController;
import controllers.callbacks.NextButtonController;
import controllers.callbacks.SeeButtonController;
import controllers.commands.LanguageController;
import controllers.commands.MessageController;
import controllers.commands.RunController;
import services.client.TelegramClient;
import services.game.GameHandler;
import services.sessions.SessionHandler;

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
