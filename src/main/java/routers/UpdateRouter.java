package routers;

import controllers.Controller;
import handlers.Handler;
import middleware.Middleware;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.parsers.ParseResult;
import services.parsers.Parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class UpdateRouter implements Router{
    private final Handler handler;
    private final Parser parser;
    private final List<Middleware> middlewares;

    public UpdateRouter(Handler handler, Parser parser, List<Middleware> middlewares) {
        this.handler = handler;
        this.parser = parser;
        this.middlewares = middlewares;
    }

    public void route(Update update) {
        String input = update.hasMessage() ? update.getMessage().getText() : update.getCallbackQuery().getData();
        ParseResult result = parser.parse(input);

        Optional<Controller> controllerOpt = handler.get(result.action()).or(() -> handler.get("message"));
        if (controllerOpt.isEmpty()) {
            return;
        }

        Consumer<Update> chain = u -> {};

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware middleware = middlewares.get(i);
            final Consumer<Update> next = chain;

            chain = u -> {
                try {
                    middleware.handle(u, next);
                }
                catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        chain.accept(update);
        Controller controller = controllerOpt.get();
        try {
            controller.handle(update, result.arguments());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
