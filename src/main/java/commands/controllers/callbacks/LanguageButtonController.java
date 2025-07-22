package commands.controllers.callbacks;

import authentication.sessions.Session;
import bot.config.AuthedConfig;
import commands.controllers.Controller;
import commands.controllers.ControllerProxy;
import exceptions.ControllerException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import authentication.client.TelegramClient;
import services.messages.AnswerDirector;
import services.messages.Builder;
import services.messages.MessageBuilder;
import services.messages.MessageDirector;
import authentication.sessions.SessionHandler;
import utilities.Language;

import java.util.Optional;

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

            AnswerDirector answerDirector = new AnswerDirector();
            AnswerCallbackQuery answer = answerDirector.constructSettingChanged(query);
            client.execute(answer);

            MessageDirector messageDirector = new MessageDirector();
            Builder builder = new MessageBuilder();

            Optional<Session> session = sessions.getSession(chat);
            if (session.isPresent()) {
                messageDirector.constructLanguageChangedMessage(builder, session.get());
                SendMessage message = builder.build();
                client.execute(message);
            }
        }).handle(config);
    }
}
