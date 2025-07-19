package commands.middleware;

import bot.config.UnAuthedConfig;
import exceptions.PipelineException;

public class ErrorHandler implements Middleware<UnAuthedConfig, PipelineException> {
    @Override
    public void handle(UnAuthedConfig config, ThrowingConsumer<UnAuthedConfig, PipelineException> next) {

    }
}
