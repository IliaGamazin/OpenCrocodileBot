package routers;

import bot.config.UpdateConfig;
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

        UpdateConfig config = new UpdateConfig(update, result.arguments());

        Controller controller = controllerOpt.get();
        Consumer<UpdateConfig> chain = getChain(controller);

        chain.accept(config);
    }

    private Consumer<UpdateConfig> getChain(Controller controller) {
        Consumer<UpdateConfig> chain = cnf -> {
            try {
                controller.handle(cnf);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware middleware = middlewares.get(i);
            final Consumer<UpdateConfig> next = chain;

            chain = u -> {
                try {
                    middleware.handle(u, next);
                }
                catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            };
        }
        return chain;
    }
}
