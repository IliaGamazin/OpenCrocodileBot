package bot.config;

import routers.Router;
import authentication.client.TelegramClient;
import services.parsers.Parser;
import authentication.sessions.SessionHandler;

public record BotConfig (
        String token,
        String name,
        Parser parser,
        TelegramClient client,
        SessionHandler sessions,
        Router router
) { }
