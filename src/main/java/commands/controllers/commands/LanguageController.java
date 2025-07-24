package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import authentication.client.TelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import services.messages.MessageDirector;

public class LanguageController implements Controller {
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public LanguageController(TelegramClient client, ControllerProxy proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            MessageDirector director = new MessageDirector();
            SendMessage message =  director.constructLanguageMessage(config.chat());
            client.execute(message);
        }).handle(config);
    }
}
