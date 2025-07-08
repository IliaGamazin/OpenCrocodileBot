package bot.config;

import routers.Router;
import services.client.TelegramClient;
import services.parsers.Parser;
import services.sessions.SessionHandler;

import java.util.Objects;

public record BotConfig (
        String token,
        String name,
        Parser parser,
        TelegramClient client,
        SessionHandler sessions,
        Router router
) {
    public BotConfig {
        Objects.requireNonNull(token, "Token must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(parser, "Parser must not be null");
        Objects.requireNonNull(client, "Client must not be null");
        Objects.requireNonNull(sessions, "Sessions must not be null");
        Objects.requireNonNull(router, "Router must not be null");
    }
}
