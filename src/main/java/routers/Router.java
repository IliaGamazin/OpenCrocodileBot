package routers;

import exceptions.PipelineException;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Router {
    void route (Update update) throws PipelineException;
}
