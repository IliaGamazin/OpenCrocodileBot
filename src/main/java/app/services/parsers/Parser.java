package app.services.parsers;

import app.commands.dto.UnAuthedConfig;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Parser {
    UnAuthedConfig parse(Update input);
}
