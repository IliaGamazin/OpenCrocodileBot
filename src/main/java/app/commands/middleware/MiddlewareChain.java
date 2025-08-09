package app.commands.middleware;

import java.util.List;

public class MiddlewareChain<T, E extends Exception>{
    private final List<Middleware<T, E>> middlewares;

    public MiddlewareChain(List<Middleware<T, E>> middlewares) {
        this.middlewares = List.copyOf(middlewares);
    }

    public void execute(T config, ThrowingConsumer<T, E> terminal) throws E {
        ThrowingConsumer<T, E> chain = terminal;

        for (int i = middlewares.size() - 1; i >= 0; i--) {
            final Middleware<T, E> middleware = middlewares.get(i);
            final ThrowingConsumer<T, E> next = chain;
            chain = conf -> middleware.handle(conf, next);
        }
        chain.accept(config);
    }
}
