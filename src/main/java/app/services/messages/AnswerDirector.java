package app.services.messages;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

public class AnswerDirector {
    public AnswerCallbackQuery constructSettingChanged(String query) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text("Settings changed successfully")
                .showAlert(false)
                .build();
    }

    public AnswerCallbackQuery constructWord(String query, String word) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text(word)
                .showAlert(true)
                .build();
    }

    public AnswerCallbackQuery constructError(String query, String error) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text(error)
                .showAlert(false)
                .build();
    }
}
