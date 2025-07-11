package controllers.commands;

import bot.config.UnAuthedUpdate;
import controllers.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.client.TelegramClient;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;

public class LanguageController implements Controller {
    private final TelegramClient client;

    public LanguageController(TelegramClient client) {
        this.client = client;
    }

    @Override
    public void handle(UnAuthedUpdate config) throws TelegramApiException {
        MessageDirector director = new MessageDirector();
        Builder builder = new MessageBuilder();

        director.constructLanguageMessage(builder, config.session());
        SendMessage message = builder.build();
        client.execute(message);
    }
}
