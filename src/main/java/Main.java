import bot.CrocodileBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            CrocodileBot bot = new CrocodileBot(System.getenv("TG_BOT_TOKEN"));
            botsApi.registerBot(bot);
            System.out.println("Started");
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
