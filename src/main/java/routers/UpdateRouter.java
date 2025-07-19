package routers;

import bot.config.UnAuthedConfig;
import commands.controllers.Controller;
import commands.handlers.Handler;
import commands.middleware.Pipeline;
import exceptions.GameException;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.parsers.ParseResult;
import services.parsers.Parser;

import java.util.Optional;

public class UpdateRouter implements Router{
    private final Handler handler;
    private final Parser parser;
    private final Pipeline pipeline;

    public UpdateRouter(Handler handler, Parser parser, Pipeline pipeline) {
        this.handler = handler;
        this.parser = parser;
        this.pipeline = pipeline;
    }

    public void route(Update update) {
        ParseResult result = parser.parse(update);

        Optional<Controller> controllerOpt = handler.get(result.action()).or(
                () -> update.hasMessage() ? handler.get("message") : Optional.empty());
        if (controllerOpt.isEmpty()) {
            return;
        }

        UnAuthedConfig config = new UnAuthedConfig(
                result.action(),
                result.chat(),
                update,
                result.arguments()
        );

        Controller controller = controllerOpt.get();
        try {
            pipeline.execute(config, controller);
        }
        catch (GameException e) {
            System.out.println("Game excpetion caught");
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
