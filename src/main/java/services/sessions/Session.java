package services.sessions;

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

    public Session withLanguage(Language language) {
        return new Session(this.chatId, language);
    }
}
