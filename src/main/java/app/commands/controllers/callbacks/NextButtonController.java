package app.commands.controllers.callbacks;

import app.authentication.client.TelegramClient;
import app.commands.dto.AuthedConfig;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import app.exceptions.GameException;
import app.exceptions.ValidationException;
import app.game.GameHandler;
import app.game.GameState;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import app.services.messages.AnswerDirector;

public class NextButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;
    private final ControllerProxy proxy;

    public NextButtonController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.client = client;
        this.games = games;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            String query = update.getCallbackQuery().getId();
            long chat = config.chat();

            GameState game = games.get(chat)
                    .orElseThrow(() -> new GameException("Game not found"));

            if (game.master() != update.getCallbackQuery().getFrom().getId()) {
                throw new ValidationException("Not enough permissions");
            }

            GameState updated = games.nextWord(chat, config.session().language())
                    .orElseThrow(() -> new GameException("Failed to get next word"));

            AnswerDirector director = new AnswerDirector();
            AnswerCallbackQuery answer = director.constructWord(query, updated.word());
            client.execute(answer);
        }).handle(config);
    }
}
