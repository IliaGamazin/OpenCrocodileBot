package app.commands.controllers.callbacks;

import app.authentication.client.TelegramClient;
import app.authentication.sessions.Session;
import app.authentication.sessions.SessionHandler;
import app.commands.dto.AuthedConfig;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import app.exceptions.GameException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import app.services.messages.AnswerDirector;
import app.services.messages.MessageDirector;
import app.utilities.Language;

public class LanguageButtonController implements Controller {
    private final SessionHandler sessions;
    private final TelegramClient client;
    private final ControllerProxy proxy;

    public LanguageButtonController(SessionHandler sessions, TelegramClient client, ControllerProxy proxy) {
        this.sessions = sessions;
        this.client = client;
        this.proxy = proxy;
    }

    @Override
    public void handle(AuthedConfig config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            String query = update.getCallbackQuery().getId();
            String code = config.args()[0];
            long chat = config.chat();

            sessions.changeLanguage(chat, Language.fromCode(code));
            Session session = sessions.getSession(chat)
                .orElseThrow(() -> new GameException("Session not found"));

            AnswerDirector answerDirector = new AnswerDirector();
            AnswerCallbackQuery answer = answerDirector.constructSettingChanged(query);
            client.execute(answer);

            MessageDirector messageDirector = new MessageDirector();
            SendMessage message = messageDirector.constructLanguageChangedMessage(chat, session.language().getTitle());;
            client.execute(message);
        }).handle(config);
    }
}
