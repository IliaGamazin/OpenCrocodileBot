package bot.config;

import org.telegram.telegrambots.meta.api.objects.Update;
import services.sessions.Session;

public record AuthedUpdate(
        String action,
        long chat,
        Update update,
        String[] arguments,
        Session session
) {}
