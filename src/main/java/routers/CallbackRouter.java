package routers;

import controllers.Controller;
import handlers.CallbackHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackRouter implements Router{
    private final CallbackHandler callbacks;
    public CallbackRouter(CallbackHandler callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void route(Update update) {
        String callback = update.getCallbackQuery().getData();
        Controller controller = callbacks.get(callback);
        controller.handle(update);
    }
}
