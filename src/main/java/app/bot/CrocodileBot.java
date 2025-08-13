package app.bot;

import app.exceptions.PipelineException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import app.routers.Router;

@Service
public class CrocodileBot extends TelegramLongPollingBot {
    private final Router router;
    private final String name;

    public CrocodileBot(@Value("${bot.token}") String token,
                        @Value("${bot.name}") String name,
                        Router router) {
        super(token);
        this.router = router;
        this.name = name;
    }

    @Override
    public void onUpdateReceived(Update update)  {
        try {
            router.route(update);
        }
        catch (PipelineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
