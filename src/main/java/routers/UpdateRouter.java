package routers;

import bot.config.UnAuthedConfig;
import commands.controllers.Controller;
import commands.handlers.CommandRepo;
import commands.middleware.Pipeline;
import exceptions.PipelineException;
import org.telegram.telegrambots.meta.api.objects.Update;
import services.parsers.Parser;

public class UpdateRouter implements Router{
    private final CommandRepo handler;
    private final Parser parser;
    private final Pipeline pipeline;

    public UpdateRouter(CommandRepo handler, Parser parser, Pipeline pipeline) {
        this.handler = handler;
        this.parser = parser;
        this.pipeline = pipeline;
    }

    public void route(Update update) throws PipelineException {
        UnAuthedConfig config = parser.parse(update);

        Controller controller = handler.get(config.action())
                .or(() -> handler.get("message"))
                .orElseThrow(()-> new PipelineException("Invalid command"));

        pipeline.execute(config, controller);
    }
}
