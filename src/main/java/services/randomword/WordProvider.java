package services.randomword;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WordProvider {
    String getRandomWord() throws IOException, URISyntaxException;
}
