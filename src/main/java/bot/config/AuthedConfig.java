package bot.config;

import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;

public record AuthedConfig(
        String action,
        long chat,
        Update update,
        String[] args,
        Session session
) {}
