//package bot.config;
//
//import handlers.CommandHandler;
//import handlers.Handler;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import routers.Router;
//import services.client.Sender;
//import services.client.TelegramClient;
//import services.parsers.Parser;
//import services.parsers.UniversalParser;
//import services.sessions.SessionHandler;
//
//public class BotConfig {
//    private final String token;
//    private final String name;
//    private final Router messageRouter;
//    private final Router callbackRouter
//    private final CommandHandler commands;
//    private final SessionHandler sessions;
//    private final Parser parser;
//    private final TelegramClient executor;
//
//    public BotConfig() {
//        sessions = new SessionHandler();
//        parser = new UniversalParser("OpenCrocodileBot");
//        commands = new CommandHandler(sessions, sender);
//    }
//
//    public void wireClient(TelegramLongPollingBot bot) {
//        sender = s;
//    }
//}
