package app.commands.middleware;

import app.authentication.client.TelegramClient;
import app.commands.dto.UnAuthedDTO;
import app.exceptions.ControllerException;
import app.exceptions.PipelineException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import app.services.messages.AnswerDirector;
import app.services.messages.MessageDirector;

public class ErrorHandler implements Middleware<UnAuthedDTO, PipelineException> {
    private final TelegramClient client;

    public ErrorHandler(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void handle(UnAuthedDTO config, ThrowingConsumer<UnAuthedDTO, PipelineException> next) throws PipelineException {
        AnswerDirector answers = new AnswerDirector();
        MessageDirector messages = new MessageDirector();
        try {
            next.accept(config);
        }
        catch (ControllerException ce) {
            try {
                if (config.update().hasCallbackQuery()) {
                    String query = config.update().getCallbackQuery().getId();
                    AnswerCallbackQuery answer = answers.constructError(query, ce.getMessage());
                    client.execute(answer);
                }
                else {
                    SendMessage message = messages.constructErrorMessage(config.chat(), ce.getMessage());
                    client.execute(message);
                }
            }
            catch (TelegramApiException ae) {
                System.err.println("Unexpected telegram api error");
                ae.printStackTrace();
            }
        }
    }
}
