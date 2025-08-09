package app.game;

import app.services.randomword.WordProvider;
import app.utilities.Language;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GameHandler {
    private final ConcurrentHashMap<Long, GameState> games;
    private final WordProvider provider;

    public GameHandler(WordProvider provider) {
        this.provider = provider;
        games = new ConcurrentHashMap<>();
    }

    public void start(long chat, long master, Language language) throws IOException, URISyntaxException {
        String word = provider.getRandomWord(language);
        GameState game = new GameState(chat, master, word);
        games.put(chat, game);
    }

    public Optional<GameState> nextWord(long chat, Language language) throws IOException, URISyntaxException {
        GameState current = games.get(chat);
        if (current == null) {
            return Optional.empty();
        }

        String newWord = provider.getRandomWord(language);
        GameState updated = current.withWord(newWord);
        games.put(chat, updated);
        return Optional.of(updated);
    }

    public void end(long chat) {
        games.remove(chat);
    }

    public Optional<GameState> get(long chat) {
        return Optional.ofNullable(games.get(chat));
    }
}
