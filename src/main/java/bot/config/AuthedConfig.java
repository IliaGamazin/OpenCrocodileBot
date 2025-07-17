package bot.config;

import authentication.sessions.Session;
import org.telegram.telegrambots.meta.api.objects.Update;

public record AuthedConfig(
        String action,
        long chat,
        Update update,
        String[] args,
        Session session
) {}
