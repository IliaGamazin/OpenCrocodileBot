package app.utilities;

public enum Language {
    RUSSIAN ("ru", "Russian", "\uD83C\uDDEC\uD83C\uDDE7"),
    ENGLISH ("en", "English", "\uD83C\uDDF7\uD83C\uDDFA"),
    UKRAINIAN ("uk", "Ukrainian", "\uD83C\uDDFA\uD83C\uDDE6");

    private final String code;
    private final String title;
    private final String unicode;

    Language(String code, String title, String unicode) {
        this.code = code;
        this.title = title;
        this.unicode = unicode;
    }

    public String getUnicode() {
        return unicode;
    }

    public String getTitle() {
        return title;
    }

    public static Language fromCode(String code) {
        for (Language language : Language.values()) {
            if (language.code.equalsIgnoreCase(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language code: " + code);
    }
}
