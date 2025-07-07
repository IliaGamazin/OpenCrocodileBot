import bot.BotFactory;
import bot.CrocodileBot;
import bot.config.TelegramBotClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            BotFactory factory = new BotFactory();
            CrocodileBot bot = factory.createBot(System.getenv("TG_BOT_TOKEN"), "OpenCrocodileBot");

            botsApi.registerBot(bot);
            System.out.println("Started");
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
