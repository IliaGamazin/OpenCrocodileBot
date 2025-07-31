package commands.middleware;

import authentication.client.TelegramClient;
import bot.config.UnAuthedConfig;
import exceptions.*;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.messages.AnswerDirector;
import services.messages.MessageDirector;

public class ErrorHandler implements Middleware<UnAuthedConfig, PipelineException> {
    private final TelegramClient client;

    public ErrorHandler(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void handle(UnAuthedConfig config, ThrowingConsumer<UnAuthedConfig, PipelineException> next) throws PipelineException {
        AnswerDirector answers = new AnswerDirector();
        MessageDirector messages = new MessageDirector();
        try {
            try {
                next.accept(config);
            }
            catch (ControllerException e) {
                if (config.update().hasCallbackQuery()) {
                    String query = config.update().getCallbackQuery().getId();
                    AnswerCallbackQuery answer = answers.constructError(query, e.getMessage());
                    client.execute(answer);
                }
                else {
                    SendMessage message = messages.constructErrorMessage(config.chat(), e.getMessage());
                    client.execute(message);
                }
            }
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
