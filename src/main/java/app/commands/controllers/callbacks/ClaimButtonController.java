package app.commands.controllers.callbacks;

import app.authentication.client.TelegramClient;
import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import app.exceptions.ValidationException;
import app.model.games.GameHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import app.services.messages.MessageDirector;

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
    public void handle(AuthedDTO config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            User from = update.getCallbackQuery().getFrom();
            long chat = config.chat();

            if (games.get(chat).isPresent()) {
                throw new ValidationException("Game is already started!");
            }

            MessageDirector director = new MessageDirector();
            SendMessage message = director.constructWordMessage(chat, from.getFirstName(), from.getId());

            EditMessageText edit = EditMessageText.builder()
                .chatId(chat)
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .text(message.getText())
                .parseMode("MarkdownV2")
                .replyMarkup(director.createWordKeyboard())
                .build();
            games.start(chat, from.getId(), config.session().getLanguage());
            client.execute(edit);
        }).handle(config);
    }
}
