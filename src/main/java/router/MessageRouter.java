package router;

import controllers.*;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageRouter implements Router{
    private final CommandHandler commands;
    public MessageRouter(CommandHandler commands) {
        this.commands = commands;
    }

    public void route (Update update) {
        String command = update.getMessage().getText();
        Controller controller = commands.get(command);
        controller.handle(update);
    }
}
