package app.services.parsers;

import app.commands.dto.UnAuthedDTO;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Parser {
    UnAuthedDTO parse(Update input);
}
