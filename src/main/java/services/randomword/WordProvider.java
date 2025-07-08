package services.randomword;

import utilities.Language;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WordProvider {
    String getRandomWord(Language language) throws IOException, URISyntaxException;
}
