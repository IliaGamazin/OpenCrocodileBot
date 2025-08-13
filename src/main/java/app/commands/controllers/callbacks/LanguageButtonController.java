package app.commands.controllers.callbacks;

import app.authentication.client.TelegramClient;
import app.model.sessions.SessionHandler;
import app.commands.dto.AuthedDTO;
import app.commands.controllers.Controller;
import app.commands.controllers.proxies.ControllerProxy;
import app.exceptions.ControllerException;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void handle(AuthedDTO config) throws ControllerException {
        proxy.wrap(conf -> {
            Update update = config.update();
            String query = update.getCallbackQuery().getId();
            String code = config.args()[0];
            long chat = config.chat();

            Language lang = Language.fromCode(code);
            sessions.changeLanguage(chat, lang);

            AnswerDirector answerDirector = new AnswerDirector();
            AnswerCallbackQuery answer = answerDirector.constructSettingChanged(query);
            client.execute(answer);

            MessageDirector messageDirector = new MessageDirector();
            SendMessage message = messageDirector.constructLanguageChangedMessage(chat, lang.getTitle());
            client.execute(message);
        }).handle(config);
    }
}
