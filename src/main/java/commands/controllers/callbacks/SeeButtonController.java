package commands.controllers.callbacks;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import authentication.client.TelegramClient;
import game.GameHandler;
import services.messages.AnswerDirector;

public class SeeButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;
    public SeeButtonController(TelegramClient client, GameHandler games) {
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
                    return director.constructWord(query, game.word());
                })
                .orElse(director.constructInactive(query));

        client.execute(answer);
    }
}
