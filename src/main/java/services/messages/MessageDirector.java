package services.messages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import services.sessions.Session;

import java.util.ArrayList;
import java.util.List;

public class MessageDirector {
    public void constructLanguageMessage(Builder builder, Session session) {
        builder.setChatId(session.getChatId());
        builder.setText("Choose the language");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton enButton = new InlineKeyboardButton();
        enButton.setText("\uD83C\uDDEC\uD83C\uDDE7");
        enButton.setCallbackData("/language/eng");

        InlineKeyboardButton ruButton = new InlineKeyboardButton();
        ruButton.setText("\uD83C\uDDF7\uD83C\uDDFA");
        ruButton.setCallbackData("/language/ru");

        InlineKeyboardButton uaButton = new InlineKeyboardButton();
        uaButton.setText("\uD83C\uDDFA\uD83C\uDDE6");
        uaButton.setCallbackData("/language/ua");

        List<InlineKeyboardButton> enRow = new ArrayList<>();
        enRow.add(enButton);

        List<InlineKeyboardButton> ruRow = new ArrayList<>();
        ruRow.add(ruButton);

        List<InlineKeyboardButton> uaRow = new ArrayList<>();
        uaRow.add(uaButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(enRow);
        rows.add(ruRow);
        rows.add(uaRow);

        markup.setKeyboard(rows);

        builder.setReplyMarkup(markup);
    }
}
