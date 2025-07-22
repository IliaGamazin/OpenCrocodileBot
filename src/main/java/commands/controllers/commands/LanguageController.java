package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.ControllerProxy;
import exceptions.ControllerException;
import authentication.client.TelegramClient;
import services.messages.Builder;
import services.messages.MessageBuilder;
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
            Builder builder = new MessageBuilder();

            director.constructLanguageMessage(builder, config.chat());
            client.execute(builder.build());
        }).handle(config);
    }
}
