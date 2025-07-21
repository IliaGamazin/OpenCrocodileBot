package commands.controllers.commands;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import exceptions.TelegramException;
import game.GameHandler;
import game.GameState;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;

import java.util.Objects;
import java.util.Optional;

public class MessageController implements Controller {
    private final GameHandler games;
    private final TelegramClient client;

    public MessageController(TelegramClient client, GameHandler games) {
        this.games = games;
        this.client = client;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        try {
            Update update = config.update();
            long chat = config.chat();

            Optional<GameState> gameOpt = games.get(config.chat());
            if (gameOpt.isEmpty()) {
                return;
            }

            GameState game = gameOpt.get();
            System.out.println(game.word());
            System.out.println(config.update().getMessage().getText());
            if (Objects.equals(game.word(), config.update().getMessage().getText())) {
                MessageDirector director = new MessageDirector();
                MessageBuilder builder = new MessageBuilder();


                System.out.println("eq");
                director.constructWinMessage(builder, chat, update.getMessage().getFrom().getFirstName());
                client.execute(builder.build());
                games.end(chat);
            }
        }
        catch (TelegramApiException e) {
            throw new TelegramException("Telegram API failed", e);
        }
    }
}
