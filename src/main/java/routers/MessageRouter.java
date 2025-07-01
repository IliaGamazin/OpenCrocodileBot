package routers;

import controllers.*;
import handlers.Handler;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.parsers.ParseResult;
import services.parsers.Parser;

public class MessageRouter implements Router{
    private final Handler commands;
    private final Parser parser;

    public MessageRouter(Handler commands, Parser parser) {
        this.commands = commands;
        this.parser = parser;
    }

    public void route (Update update) {
        String input = update.getMessage().getText();
        ParseResult result = parser.parse(input);
        Controller controller = commands.get(result.action())
                .or(() -> commands.get("message"))
                .orElseThrow();
        controller.handle(update, result.arguments());
    }
}
