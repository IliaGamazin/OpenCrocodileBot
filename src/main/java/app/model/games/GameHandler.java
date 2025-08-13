package app.model.games;

import app.services.randomword.WordProvider;
import app.utilities.Language;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Repository
@Transactional
public class GameHandler {
    private final GameRepository games;
    private final WordProvider provider;

    public GameHandler(GameRepository games, WordProvider provider) {
        this.games = games;
        this.provider = provider;
    }

    public void start(long chat, long master, Language language) throws IOException, URISyntaxException {
        String word = provider.getRandomWord(language);
        Game game = new Game(chat, master, word);
        games.save(game);
    }

    public Optional<Game> nextWord(long chat, Language language) throws IOException, URISyntaxException {
        Optional<Game> current = games.findById(chat);
        if (current.isEmpty()) {
            return Optional.empty();
        }

        String word = provider.getRandomWord(language);
        Game game = current.get();
        game.setWord(word);
        games.save(game);
        return Optional.of(game);
    }

    public void end(long chat) {
        games.deleteById(chat);
    }

    public Optional<Game> get(long chat) {
        return games.findById(chat);
    }
}
