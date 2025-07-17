package services.messages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import game.GameState;
import authentication.sessions.Session;

import java.util.ArrayList;
import java.util.List;

public class MessageDirector {
    public void constructLanguageMessage(Builder builder, Session session) {
        builder.setChatId(session.chat());
        builder.setText("Choose the language");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        InlineKeyboardButton enButton = new InlineKeyboardButton();
        enButton.setText("\uD83C\uDDEC\uD83C\uDDE7");
        enButton.setCallbackData("language-callback en");

        InlineKeyboardButton ruButton = new InlineKeyboardButton();
        ruButton.setText("\uD83C\uDDF7\uD83C\uDDFA");
        ruButton.setCallbackData("language-callback ru");

        InlineKeyboardButton uaButton = new InlineKeyboardButton();
        uaButton.setText("\uD83C\uDDFA\uD83C\uDDE6");
        uaButton.setCallbackData("language-callback ua");

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

    public void constructWordMessage(Builder builder, Session session, GameState game) {
        builder.setChatId(session.chat());
        builder.setText(game.master() + " explains the word");

        InlineKeyboardButton seeButton = new InlineKeyboardButton();
        seeButton.setText("\uD83D\uDD0D See word");
        seeButton.setCallbackData("see-callback");

        InlineKeyboardButton nextButton = new InlineKeyboardButton();
        nextButton.setText("Next word ⏩");
        nextButton.setCallbackData("next-callback");

        List<InlineKeyboardButton> seeRow = new ArrayList<>();
        seeRow.add(seeButton);

        List<InlineKeyboardButton> nextRow = new ArrayList<>();
        nextRow.add(nextButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(seeRow);
        rows.add(nextRow);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        builder.setReplyMarkup(markup);
    }

//    public void constructWinMessage(Builder builder, Session session, ) {
//        builder.setChatId(session.chat());
//        builder.setText(session.);
//    }

    public void constructLanguageChangedMessage(Builder builder, Session session) {
        builder.setChatId(session.chat());
        builder.setText("Language changed to " + session.language().getTitle());
    }
}
