package app.authentication.sessions;

import app.utilities.Language;

public record Session(long chat, Language language) {
    public Session withLanguage(Language language) {
        return new Session(this.chat, language);
    }
}
