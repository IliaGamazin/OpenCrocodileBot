package services.messages;

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

    public AnswerCallbackQuery constructInactive(String query) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text("The game is inactive")
                .showAlert(false)
                .build();
    }

    public AnswerCallbackQuery constructNotMaster(String query) {
        return AnswerCallbackQuery.builder()
                .callbackQueryId(query)
                .text("You're not the game master!")
                .showAlert(false)
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
