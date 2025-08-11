package app.commands.dto;

import app.authentication.sessions.Session;
import org.telegram.telegrambots.meta.api.objects.Update;

public record AuthedConfig(
        String action,
        Update update,
        Session session,
        String[] args,
        long chat
) {}
