package commands.controllers.callbacks;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import authentication.client.TelegramClient;
import game.GameHandler;
import game.GameState;
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
    public void handle(AuthedConfig config) throws TelegramApiException {
        Update update = config.update();
        String query = update.getCallbackQuery().getId();

        AnswerDirector director = new AnswerDirector();

        AnswerCallbackQuery answer = games.get(config.chat())
                .map(game -> {
                    if (game.master() != update.getCallbackQuery().getFrom().getId()) {
                        return director.constructNotMaster(query);
                    }

                    try {
                        GameState updatedGame = games.nextWord(config.chat(), config.session().language())
                                .orElseThrow(() -> new RuntimeException("Failed to get next word"));
                        return director.constructWord(query, updatedGame.word());
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                        return director.constructError(query, "Failed to get next word. Try again.");
                    }
                })
                .orElse(director.constructInactive(query));

        client.execute(answer);
    }
}
