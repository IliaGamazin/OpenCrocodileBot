package routers;

import controllers.Controller;
import handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.parsers.ParseResult;
import services.parsers.Parser;

import java.util.Optional;

public class CallbackRouter implements Router{
    private final Handler callbacks;
    private final Parser parser;
    public CallbackRouter(Handler callbacks, Parser parser) {
        this.callbacks = callbacks;
        this.parser = parser;
    }

    @Override
    public void route(Update update) {
        String input = update.getCallbackQuery().getData();
        ParseResult result = parser.parse(input);
        Optional<Controller> controller = callbacks.get(result.action());
        controller.ifPresent(value -> value.handle(update));
    }
}
