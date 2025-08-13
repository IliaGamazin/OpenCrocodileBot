package app.model.games;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @Id
    private long id;

    private long master;
    private String word;

    public Game(long id, Long master, String word) {
        this.id = id;
        this.master = master;
        this.word = word;
    }

    public Game() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }
}

