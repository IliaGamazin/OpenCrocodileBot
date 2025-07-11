package routers;

import bot.config.UnAuthedUpdate;
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
        ParseResult result = parser.parse(update);

        Optional<Controller> controllerOpt = handler.get(result.action()).or(
                () -> update.hasMessage() ? handler.get("message") : Optional.empty());
        if (controllerOpt.isEmpty()) {
            return;
        }

        UnAuthedUpdate config = new UnAuthedUpdate(result.chat(), update, result.arguments(), null);

        Controller controller = controllerOpt.get();
        Consumer<UnAuthedUpdate> chain = getChain(controller);

        chain.accept(config);
    }

    private Consumer<UnAuthedUpdate> getChain(Controller controller) {
        Consumer<UnAuthedUpdate> chain = cnf -> {
            try {
                controller.handle(cnf);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware middleware = middlewares.get(i);
            final Consumer<UnAuthedUpdate> next = chain;

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
