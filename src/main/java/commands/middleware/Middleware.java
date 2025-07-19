package commands.middleware;

@FunctionalInterface
public interface Middleware<T, E extends Exception> {
    void handle(T config, ThrowingConsumer<T, E> next) throws E;
}
