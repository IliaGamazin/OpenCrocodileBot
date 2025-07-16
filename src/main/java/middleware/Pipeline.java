package middleware;

import bot.config.AuthedConfig;
import bot.config.UnAuthedConfig;
import controllers.Controller;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.sessions.Session;

public class Pipeline {
    private final MiddlewareChain<UnAuthedConfig> preAuthChain;
    private final MiddlewareChain<AuthedConfig> postAuthChain;
    private final AuthBridge authBridge;

    public Pipeline(
            MiddlewareChain<UnAuthedConfig> preAuthChain,
            MiddlewareChain<AuthedConfig> postAuthChain,
            AuthBridge authBridge) {
        this.preAuthChain = preAuthChain;
        this.postAuthChain = postAuthChain;
        this.authBridge = authBridge;
    }

    public void execute(UnAuthedConfig config, Controller controller) {
        preAuthChain.execute(config, unAuthed -> {
            try {
                Session session = authBridge.authenticate(unAuthed);
                AuthedConfig authed = new AuthedConfig(
                        unAuthed.action(),
                        unAuthed.chat(),
                        unAuthed.update(),
                        unAuthed.args(),
                        session
                );
                postAuthChain.execute(authed, authedConfig -> {
                    try {
                        controller.handle(authedConfig);
                    }
                    catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
