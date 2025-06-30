package routers;

import controllers.*;
import handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageRouter implements Router{
    private final Handler commands;
    public MessageRouter(Handler commands) {
        this.commands = commands;
    }

    public void route (Update update) {
        String command = update.getMessage().getText();
        Controller controller = commands.get(command)
                .or(() -> commands.get("/message"))
                .orElseThrow();
        controller.handle(update);
    }
}
