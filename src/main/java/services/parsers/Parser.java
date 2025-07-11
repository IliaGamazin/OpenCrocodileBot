package services.parsers;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Parser {
    ParseResult parse(Update input);
}
