package commands.controllers.callbacks;

import authentication.client.TelegramClient;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import game.GameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import services.messages.MessageDirector;

public class ClaimButtonController implements Controller {
    private final TelegramClient client;
    private final GameHandler games;
    private final ControllerProxy proxy;

    public ClaimButtonController(TelegramClient client, GameHandler games, ControllerProxy proxy) {
        this.games = games;
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            long chat = config.chat();
            User from = update.getCallbackQuery().getFrom();

            MessageDirector director = new MessageDirector();
            SendMessage message = director.constructWordMessage(chat, from.getFirstName(), from.getId());

            EditMessageText edit = EditMessageText.builder()
                .chatId(chat)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .text(message.getText())
                .replyMarkup(director.createWordKeyboard())
                .build();
            games.start(chat, from.getId(), config.session().language());
            client.execute(edit);
        }).handle(config);
    }
}
