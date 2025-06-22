package controllers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import services.sender.Sender;
import services.sessions.SessionHandler;

import java.util.ArrayList;
import java.util.List;

public class LanguageController implements Controller{
    private final SessionHandler sessions;
    private final Sender sender;

    public LanguageController(SessionHandler sessions, Sender sender) {
        this.sessions = sessions;
        this.sender = sender;
    }
    @Override
    public void handle(Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Сова игры 13");
        button.setCallbackData("/endpoint");
        row.add(button);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);
    }
}
