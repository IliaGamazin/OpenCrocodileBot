package app.commands.middleware;

import app.authentication.AuthBridge;
import app.authentication.sessions.Session;
import app.commands.dto.AuthedConfig;
import app.commands.dto.UnAuthedConfig;
import app.commands.controllers.Controller;
import app.exceptions.PipelineException;

public class MiddlewarePipeline implements Pipeline{
    private final MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain;
    private final MiddlewareChain<AuthedConfig, PipelineException> postAuthChain;
    private final AuthBridge authBridge;

    public MiddlewarePipeline(
            MiddlewareChain<UnAuthedConfig, PipelineException> preAuthChain,
            MiddlewareChain<AuthedConfig, PipelineException> postAuthChain,
            AuthBridge authBridge) {
        this.preAuthChain = preAuthChain;
        this.postAuthChain = postAuthChain;
        this.authBridge = authBridge;
    }

    @Override
    public void execute(UnAuthedConfig config, Controller controller) throws PipelineException {
        preAuthChain.execute(config, unAuthed -> {
                Session session = authBridge.authenticate(unAuthed);
                AuthedConfig authed = new AuthedConfig(
                        unAuthed.action(),
                        unAuthed.update(),
                        session,
                        unAuthed.args(),
                        unAuthed.chat()
                );
                postAuthChain.execute(authed, controller::handle);
        });
    }
}
