package app.commands.middleware;

import app.commands.dto.UnAuthedConfig;
import app.commands.controllers.Controller;
import app.exceptions.PipelineException;

public interface Pipeline {
    void execute(UnAuthedConfig config, Controller controller) throws PipelineException;
}
