package services.randomword.wiktionary;

import utilities.Language;
import services.randomword.WordProvider;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class WiktionaryProvider implements WordProvider {
    @Override
    public String getRandomWord(Language language) throws IOException, URISyntaxException {
        String currentUrl = String.format(
                "https://en.wiktionary.org/wiki/Special:RandomInCategory/%s_lemmas",
                language.getTitle()
        );
        URI uri = new URI(currentUrl);

        for (int redirectCount = 0; redirectCount < 2; redirectCount++) {
            HttpsURLConnection connection = (HttpsURLConnection) uri.toURL().openConnection();

            connection.setInstanceFollowRedirects(false);

            int responseCode = connection.getResponseCode();

            // Since we just scrap the link, immediate 200 OK means we didn't get redirected to a random word

            if (responseCode == 200) {
                connection.disconnect();
                throw new IOException("Wiktionary response with no redirect indicates an error " + responseCode);
            }

            // Error handling

            else if (responseCode > 400) {
                connection.disconnect();
                throw new IOException("Invalid wiktionary response " + responseCode);
            }

            // Redirection handling, as long as we get 300-399, keep following

            else if (responseCode >= 300 && responseCode < 400) {
                String redirectUrl = connection.getHeaderField("Location");
                connection.disconnect();

                if (redirectUrl != null) {
                    if (redirectUrl.startsWith("/")) {
                        redirectUrl = "https://en.wiktionary.org" + redirectUrl;
                    }
                    currentUrl = redirectUrl;
                }
                else {
                    connection.disconnect();
                    throw new IOException("Received redirect status " + responseCode + " but no Location header");
                }
            }
        }

        String word = currentUrl.substring(currentUrl.lastIndexOf('/') + 1).replace("_", " ");
        return URLDecoder.decode(word, StandardCharsets.UTF_8);
    }
}

