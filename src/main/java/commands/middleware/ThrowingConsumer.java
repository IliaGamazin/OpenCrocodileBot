package commands.middleware;

import exceptions.ControllerException;

@FunctionalInterface
public interface ThrowingConsumer <T, E extends Exception> {
    void accept(T t) throws E, ControllerException;
}
