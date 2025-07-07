package routers;

import controllers.Controller;
import handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.parsers.ParseResult;
import services.parsers.Parser;

import java.util.Optional;

public class UpdateRouter implements Router{
    private final Handler handler;
    private final Parser parser;

    public UpdateRouter(Handler handler, Parser parser) {
        this.handler = handler;
        this.parser = parser;
    }

    public void route(Update update) {
        String input = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        ParseResult result = parser.parse(input);

        Optional<Controller> controller = handler.get(result.action()).or(() -> handler.get("message"));
        if (controller.isPresent()) {
            try {
                controller.get().handle(update, result.arguments());
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
