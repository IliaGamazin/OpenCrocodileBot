package controllers.callbacks;

import bot.config.UnAuthedUpdate;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.game.GameHandler;
import services.game.GameState;
import services.messages.AnswerDirector;

import java.util.Optional;

public class SeeButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;
    public SeeButtonController(TelegramClient client, GameHandler games) {
        this.client = client;
        this.games = games;
    }

    @Override
    public void handle(UnAuthedUpdate config) throws TelegramApiException {
        Update update = config.update();
        String query = update.getCallbackQuery().getId();
        Optional<GameState> gameOpt = games.get(config.chat());

        AnswerDirector director = new AnswerDirector();
        AnswerCallbackQuery answer;
        if (gameOpt.isEmpty()) {
            answer = director.constructInactive(query);
        }
        else {
            answer = director.constructWord(query, gameOpt.get().word());
        }
        
        client.execute(answer);
    }
}
