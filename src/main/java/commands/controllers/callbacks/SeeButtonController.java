package commands.controllers.callbacks;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.ControllerProxy;
import exceptions.ControllerException;
import exceptions.GameException;
import exceptions.ValidationException;
import game.GameState;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import authentication.client.TelegramClient;
import game.GameHandler;
import services.messages.AnswerDirector;

public class SeeButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;
    private final ControllerProxy proxy;

    public SeeButtonController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.client = client;
        this.games = games;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            String query = update.getCallbackQuery().getId();

            AnswerDirector director = new AnswerDirector();
            GameState game = games.get(config.chat())
                    .orElseThrow(() -> new GameException("Game not found"));

            if (game.master() != update.getCallbackQuery().getFrom().getId()) {
                throw new ValidationException("Not enough permissions");
            }

            AnswerCallbackQuery answer = director.constructWord(query, game.word());
            client.execute(answer);
        }).handle(config);
    }
}
