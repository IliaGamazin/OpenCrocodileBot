package commands.controllers.commands;

import bot.config.AuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import exceptions.TelegramException;
import authentication.client.TelegramClient;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;

public class LanguageController implements Controller {
    private final TelegramClient client;

    public LanguageController(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        try {
            MessageDirector director = new MessageDirector();
            Builder builder = new MessageBuilder();

            director.constructLanguageMessage(builder, config.chat());
            client.execute(builder.build());
        }
        catch (TelegramApiException e) {
            throw new TelegramException("Telegram API failed", e);
        }
    }
}
