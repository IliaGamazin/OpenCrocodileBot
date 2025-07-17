package commands.middleware;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.Consumer;

public class MiddlewareChain<T>{
    private final List<Middleware<T>> middlewares;

    public MiddlewareChain(List<Middleware<T>> middlewares) {
        this.middlewares = List.copyOf(middlewares);
    }

    public void execute(T config, Consumer<T> terminal) {
        Consumer<T> chain = terminal;

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware<T> middleware = middlewares.get(i);
            final Consumer<T> next = chain;
            chain = conf -> {
                try {
                    middleware.handle(conf, next);
                }
                catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            };
        }
        chain.accept(config);
    }
}
