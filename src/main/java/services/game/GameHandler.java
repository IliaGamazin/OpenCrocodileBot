package services.game;

import services.randomword.WordProvider;
import utilities.Language;

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

    public GameState start(long chat, long master, Language language) throws IOException, URISyntaxException {
        String word = provider.getRandomWord(language);
        GameState game = new GameState(chat, master, word);
        games.put(chat, game);
        return game;
    }

    public Optional<GameState> get(long chat) {
        return Optional.ofNullable(games.get(chat));
    }
}
