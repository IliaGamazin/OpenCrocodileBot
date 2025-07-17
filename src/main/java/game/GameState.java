package game;

public record GameState(
        long chat,
        long master,
        String word
) {
    public GameState withWord(String word) {
        return new GameState(this.chat, this.master, word);
    }
}

