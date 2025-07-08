package services.sessions;

import utilities.Language;

public record Session(long chat, Language language) {
    public Session withLanguage(Language language) {
        return new Session(this.chat, language);
    }
}
