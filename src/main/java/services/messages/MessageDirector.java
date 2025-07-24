package services.messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class MessageDirector {
    public SendMessage constructLanguageMessage(long chat) {
        return SendMessage.builder()
            .chatId(chat)
            .text("Choose the language:")
            .replyMarkup(createLanguageKeyboard())
            .build();
    }

    public SendMessage constructWordMessage(long chat, String master) {
        return SendMessage.builder()
            .chatId(chat)
            .text(master + " explains the word")
            .replyMarkup(createWordKeyboard())
            .build();
    }

    public SendMessage constructWinMessage(long chat, String winner) {
        return SendMessage.builder()
            .chatId(chat)
            .text(winner + " won")
            .replyMarkup(createWinKeyboard())
            .build();
    }

    public SendMessage constructLanguageChangedMessage(long chat, String language) {
        return SendMessage.builder()
            .chatId(chat)
            .text("Language changed to " + language)
            .build();
    }

    public SendMessage constructErrorMessage(long chat, String message) {
        return SendMessage.builder()
            .chatId(chat)
            .text(message)
            .build();
    }

    private ReplyKeyboard createLanguageKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton("\uD83C\uDDEC\uD83C\uDDE7", "language-callback en"))
            .keyboardRow(createRowButton("\uD83C\uDDF7\uD83C\uDDFA", "language-callback ru"))
            .keyboardRow(createRowButton("\uD83C\uDDFA\uD83C\uDDE6", "language-callback ua"))
            .build();
    }

    private ReplyKeyboard createWordKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton("\uD83D\uDD0D See word", "see-callback"))
            .keyboardRow(createRowButton("Next word ⏩", "next-callback"))
            .build();
    }

    private ReplyKeyboard createWinKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton("Claim", "claim-callback"))
            .build();
    }

    private List<InlineKeyboardButton> createRowButton(String text, String callback) {
        return List.of(
            InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callback)
                .build()
        );
    }
}
