package commands.controllers.callbacks;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import exceptions.GameException;
import exceptions.ValidationException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import authentication.client.TelegramClient;
import game.GameHandler;
import game.GameState;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.messages.AnswerDirector;

import java.io.IOException;
import java.net.URISyntaxException;


public class NextButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;

    public NextButtonController(TelegramClient client, GameHandler games) {
        this.client = client;
        this.games = games;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        try {
            Update update = config.update();
            String query = update.getCallbackQuery().getId();
            AnswerDirector director = new AnswerDirector();

            GameState game = games.get(config.chat())
                    .orElseThrow(() -> new GameException("Game not found"));

            if (game.master() != update.getCallbackQuery().getFrom().getId()) {
                throw new ValidationException("Not enough permissions");
            }

            GameState updated = games.nextWord(config.chat(), config.session().language())
                    .orElseThrow(() -> new GameException("Failed to get next word"));

            AnswerCallbackQuery answer = director.constructWord(query, updated.word());
            client.execute(answer);
        }
        catch (IOException | URISyntaxException | TelegramApiException e) {
            throw new GameException("Service failed", e);
        }
    }
}
