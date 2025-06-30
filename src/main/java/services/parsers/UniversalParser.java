package services.parsers;

import java.util.Arrays;

public class UniversalParser implements Parser{
    private final String name;
    public UniversalParser(String name) {
        this.name = name;
    }

    @Override
    public ParseResult parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ParseResult("", new String[0]);
        }
        String cleaned = input.replaceFirst("^/", "")
                .replace("@" + name, "")
                .trim();
        String[] parts = cleaned.split("\\s+");
        String action = parts[0];
        String[] args = parts.length > 1 ?
                Arrays.copyOfRange(parts, 1, parts.length) :
                new String[0];

        return new ParseResult(action, args);
    }
}
