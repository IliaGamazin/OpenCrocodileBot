package commands.controllers.proxies;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.middleware.ThrowingConsumer;
import exceptions.ControllerException;
import exceptions.GameException;
import exceptions.TelegramException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ExceptionProxy implements ControllerProxy {

    @Override
    public Controller wrap(ThrowingConsumer<AuthedConfig, Exception> logic) {
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
                throw new GameException("Service failed", e);
            }
            catch (Exception e) {
                throw new ControllerException("Controller failed", e) ;
            }
        };
    }
}
