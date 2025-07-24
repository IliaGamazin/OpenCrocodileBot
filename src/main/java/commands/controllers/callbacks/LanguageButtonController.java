package commands.controllers.callbacks;

import authentication.sessions.Session;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.proxies.ControllerProxy;
import exceptions.ControllerException;
import exceptions.GameException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import authentication.client.TelegramClient;
import services.messages.AnswerDirector;
import services.messages.MessageDirector;
import authentication.sessions.SessionHandler;
import utilities.Language;

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
            long chat = config.chat();

            String query = update.getCallbackQuery().getId();
            String code = config.args()[0];

            sessions.changeLanguage(chat, Language.fromCode(code));
            Session session = sessions.getSession(chat).orElseThrow(() -> new GameException("Session not found"));

            AnswerDirector answerDirector = new AnswerDirector();
            AnswerCallbackQuery answer = answerDirector.constructSettingChanged(query);
            client.execute(answer);

            MessageDirector messageDirector = new MessageDirector();
            SendMessage message = messageDirector.constructLanguageChangedMessage(chat, session.language().getTitle());;
            client.execute(message);
        }).handle(config);
    }
}
