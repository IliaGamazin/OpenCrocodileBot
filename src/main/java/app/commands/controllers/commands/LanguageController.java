package app.commands.controllers.commands;

import app.authentication.client.TelegramClient;
import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import app.services.messages.MessageDirector;

public class LanguageController implements Controller {
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public LanguageController(TelegramClient client, ControllerProxy proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedDTO config) throws ControllerException {
        proxy.wrap(conf -> {
            MessageDirector director = new MessageDirector();
            SendMessage message =  director.constructLanguageMessage(config.chat());
            client.execute(message);
        }).handle(config);
    }
}
