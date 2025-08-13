package app.commands.middleware;

import app.commands.dto.UnAuthedDTO;
import app.commands.controllers.Controller;
import app.exceptions.PipelineException;

public interface Pipeline {
    void execute(UnAuthedDTO config, Controller controller) throws PipelineException;
}
