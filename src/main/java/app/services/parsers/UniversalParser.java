package app.services.parsers;

import app.commands.dto.UnAuthedConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

@Service
public class UniversalParser implements Parser{
    private final String name;
    public UniversalParser(@Value("${bot.name}") String name) {
        this.name = name;
    }

    @Override
    public UnAuthedConfig parse(Update update) {
        String input = update.hasMessage() ? update.getMessage().getText() :
                update.getCallbackQuery().getData();
        long chat = update.hasMessage() ? update.getMessage().getChatId() :
                update.getCallbackQuery().getMessage().getChatId();

        if (input == null || input.trim().isEmpty()) {
            return new UnAuthedConfig("", update, new String[0], chat);
        }

        String cleaned = input.replaceFirst("^/", "")
                .replace("@" + name, "")
                .trim();
        String[] parts = cleaned.split("\\s+");

        String action = parts[0];
        String[] args = parts.length > 1 ?
                Arrays.copyOfRange(parts, 1, parts.length) :
                new String[0];

        return new UnAuthedConfig(action, update, args, chat);
    }
}
