package services.sessions;

public class GameState {
    private final long guesser;
    private String word;

    public GameState(long guesser, String word) {
        this.guesser = guesser;
        this.word = word;
    }

    public long getGuesser() {
        return guesser;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
