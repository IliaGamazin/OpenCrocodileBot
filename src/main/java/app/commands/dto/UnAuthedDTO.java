package app.commands.dto;

import org.telegram.telegrambots.meta.api.objects.Update;

public record UnAuthedDTO(
        String action,
        Update update,
        String[] args,
        long chat
) {}
