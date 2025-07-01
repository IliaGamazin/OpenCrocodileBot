package utilities;

public enum Language {
    RUSSIAN ("ru", "Russian"),
    ENGLISH ("en", "English"),
    UKRAINIAN ("ua", "Ukrainian");

    private final String code;
    private final String title;

    Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
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
