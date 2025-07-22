package services.parsers;

import bot.config.UnAuthedConfig;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Parser {
    UnAuthedConfig parse(Update input);
}
