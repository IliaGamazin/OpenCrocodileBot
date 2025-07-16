package bot.config;

import org.telegram.telegrambots.meta.api.objects.Update;

public record UnAuthedConfig(
        String action,
        long chat,
        Update update,
        String[] args
) {}
