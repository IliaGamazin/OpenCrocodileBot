package services.sessions;

public class GameState {
    private final long master;
    private final String word;

    public GameState(long guesser, String word) {
        this.master = guesser;
        this.word = word;
    }

    public long getMaster() {
        return master;
    }

    public String getWord() {
        return word;
    }
}
