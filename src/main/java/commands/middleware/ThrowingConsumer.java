package commands.middleware;

@FunctionalInterface
public interface ThrowingConsumer <T, E extends Exception> {
    void accept(T t) throws E;
}
