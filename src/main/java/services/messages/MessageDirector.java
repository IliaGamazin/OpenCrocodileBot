package services.messages;

import org.apache.commons.codec.language.bm.Lang;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import utilities.Language;

import java.util.List;

public class MessageDirector {
    private SendMessage.SendMessageBuilder setDefaultBuilder(long chat) {
        return SendMessage.builder()
            .chatId(chat)
            .parseMode("MarkdownV2");
    }

    public SendMessage constructLanguageMessage(long chat) {
        return setDefaultBuilder(chat)
            .text("Choose the language:")
            .replyMarkup(createLanguageKeyboard())
            .build();
    }

    public SendMessage constructWordMessage(long chat, String name, long id) {
        return setDefaultBuilder(chat)
            .text("[" + escape(name) + "](tg://user?id=" + id + ") explains the word\\! \uD83D\uDED6")
            .replyMarkup(createWordKeyboard())
            .build();
    }

    public SendMessage constructLoseMessage(long chat, String name, long id) {
        return setDefaultBuilder(chat)
            .text("[" + escape(name) + "](tg://user?id=" + id + ") lost :\\( \uD83E\uDD13")
            .replyMarkup(createWinKeyboard())
            .build();
    }

    public SendMessage constructWinMessage(long chat, String name, long id) {
        return setDefaultBuilder(chat)
            .text("[" + escape(name) + "](tg://user?id=" + id + ") won\\! \uD83C\uDF89")
            .replyMarkup(createWinKeyboard())
            .build();
    }

    public SendMessage constructLanguageChangedMessage(long chat, String language) {
        return setDefaultBuilder(chat)
            .text("Language changed to " + language)
            .build();
    }

    public SendMessage constructErrorMessage(long chat, String message) {
        return SendMessage.builder()
            .chatId(chat)
            .text(message)
            .build();
    }

    private InlineKeyboardMarkup createLanguageKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton(Language.ENGLISH.getUnicode(), "language-callback en"))
            .keyboardRow(createRowButton(Language.RUSSIAN.getUnicode(), "language-callback ru"))
            .keyboardRow(createRowButton(Language.UKRAINIAN.getUnicode(), "language-callback uk"))
            .build();
    }

    public InlineKeyboardMarkup createWordKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton("\uD83D\uDD0D See word", "see-callback"))
            .keyboardRow(createRowButton("Next word ⏩", "next-callback"))
            .build();
    }

    private InlineKeyboardMarkup createWinKeyboard() {
        return InlineKeyboardMarkup.builder()
            .keyboardRow(createRowButton("Claim \uD83D\uDD90️", "claim-callback"))
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

    private String escape(String text) {
        return text.replaceAll("([_*\\[\\]()~`>#+\\-=|{}.!])", "\\\\$1");
    }
}
