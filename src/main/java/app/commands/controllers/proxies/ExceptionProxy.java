package app.commands.controllers.proxies;

import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.middleware.ThrowingConsumer;
import app.exceptions.ControllerException;
import app.exceptions.GameException;
import app.exceptions.TelegramException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public class ExceptionProxy implements ControllerProxy {

    @Override
    public Controller wrap(ThrowingConsumer<AuthedDTO, Exception> logic) {
        return config -> {
            try {
                logic.accept(config);
            }
            catch (ControllerException e) {
                throw e;
            }
            catch (TelegramApiException e) {
                throw new TelegramException("Telegram API failed", e);
            }
            catch (IOException | URISyntaxException e) {
                throw new GameException("Web service failed", e);
            }
            catch (Exception e) {
                throw new ControllerException("Controller failed", e) ;
            }
        };
    }
}
