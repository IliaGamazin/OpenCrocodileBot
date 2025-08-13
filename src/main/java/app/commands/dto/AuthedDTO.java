package app.commands.dto;

import app.model.sessions.Session;
import org.telegram.telegrambots.meta.api.objects.Update;

public record AuthedDTO(
        String action,
        Update update,
        Session session,
        String[] args,
        long chat
) {}
