package services.parsers;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public class UniversalParser implements Parser{
    private final String name;
    public UniversalParser(String name) {
        this.name = name;
    }

    @Override
    public ParseResult parse(Update update) {
        String input = update.hasMessage() ? update.getMessage().getText() :
                update.getCallbackQuery().getData();
        long chat = update.hasMessage() ? update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();

        if (input == null || input.trim().isEmpty()) {
            return new ParseResult(chat, "", new String[0]);
        }

        String cleaned = input.replaceFirst("^/", "")
                .replace("@" + name, "")
                .trim();
        String[] parts = cleaned.split("\\s+");
        String action = parts[0];
        String[] args = parts.length > 1 ?
                Arrays.copyOfRange(parts, 1, parts.length) :
                new String[0];

        return new ParseResult(chat, action, args);
    }
}
