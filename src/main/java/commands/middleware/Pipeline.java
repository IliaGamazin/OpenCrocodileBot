package commands.middleware;

import authentication.AuthBridge;
import authentication.sessions.Session;
import bot.config.AuthedConfig;
import bot.config.UnAuthedConfig;
import commands.controllers.Controller;
import exceptions.ControllerException;
import exceptions.PipelineException;

public class Pipeline {
    private final MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain;
    private final MiddlewareChain<AuthedConfig, PipelineException> postAuthChain;
    private final AuthBridge authBridge;

    public Pipeline(
            MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain,
            MiddlewareChain<AuthedConfig, PipelineException> postAuthChain,
            AuthBridge authBridge) {
        this.preAuthChain = preAuthChain;
        this.postAuthChain = postAuthChain;
        this.authBridge = authBridge;
    }

    public void execute(UnAuthedConfig config, Controller controller) throws PipelineException {
        preAuthChain.execute(config, unAuthed -> {
                Session session = authBridge.authenticate(unAuthed);
                AuthedConfig authed = new AuthedConfig(
                        unAuthed.action(),
                        unAuthed.chat(),
                        unAuthed.update(),
                        unAuthed.args(),
                        session
                );
                postAuthChain.execute(authed, controller::handle);
        });
    }
}
