package app.routers;

import app.commands.dto.UnAuthedConfig;
import app.commands.controllers.Controller;
import app.commands.handlers.CommandRepo;
import app.commands.middleware.Pipeline;
import app.exceptions.PipelineException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import app.services.parsers.Parser;

@Component
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
        Controller controller = handler.getOrDefault(config.action());
        pipeline.execute(config, controller);
    }
}
