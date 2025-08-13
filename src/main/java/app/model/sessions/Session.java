package app.model.sessions;

import app.utilities.Language;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Session implements Serializable {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    public Session() {}

    public Session(long id, Language language) {
        this.id = id;
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
