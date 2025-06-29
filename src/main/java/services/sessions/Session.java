package services.sessions;

import org.telegram.telegrambots.meta.api.objects.games.Game;
import utilities.Language;

public class Session {
    private final long chatId;
    private final Language language;

    public Session(long chatId, Language language) {
        this.chatId = chatId;
        this.language = language;
    }

    public long getChatId() {
        return chatId;
    }

    public Language getLanguage() {
        return language;
    }
}
