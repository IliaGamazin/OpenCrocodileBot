package commands.middleware;

import authentication.client.TelegramClient;
import bot.config.UnAuthedConfig;
import exceptions.*;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.messages.AnswerDirector;

public class ErrorHandler implements Middleware<UnAuthedConfig, PipelineException> {
    private final TelegramClient client;

    public ErrorHandler(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void handle(UnAuthedConfig config, ThrowingConsumer<UnAuthedConfig, PipelineException> next) throws PipelineException {
        AnswerDirector director = new AnswerDirector();
        try {
            try {
                next.accept(config);
            }
            catch (ValidationException e) {
                if (config.update().hasCallbackQuery()) {
                    String query = config.update().getCallbackQuery().getId();
                    AnswerCallbackQuery answer = director.constructNotMaster(query);
                    client.execute(answer);
                }
            }
            catch (GameException e) {
                if (config.update().hasCallbackQuery()) {
                    String query = config.update().getCallbackQuery().getId();
                    AnswerCallbackQuery answer = director.constructInactive(query);
                    client.execute(answer);
                }
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
