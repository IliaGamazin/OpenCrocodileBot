package routers;

import controllers.Controller;
import handlers.CallbackHandler;
import handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class CallbackRouter implements Router{
    private final Handler callbacks;
    public CallbackRouter(Handler callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void route(Update update) {
        String callback = update.getCallbackQuery().getData();
        Optional<Controller> controller = callbacks.get(callback);
        controller.ifPresent(value -> value.handle(update));
    }
}
