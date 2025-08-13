package app.commands.middleware;

import app.authentication.AuthBridge;
import app.model.sessions.Session;
import app.commands.dto.AuthedDTO;
import app.commands.dto.UnAuthedDTO;
import app.commands.controllers.Controller;
import app.exceptions.PipelineException;

public class MiddlewarePipeline implements Pipeline{
    private final MiddlewareChain<UnAuthedDTO, PipelineException> preAuthChain;
    private final MiddlewareChain<AuthedDTO, PipelineException> postAuthChain;
    private final AuthBridge authBridge;

    public MiddlewarePipeline(
            MiddlewareChain<UnAuthedDTO, PipelineException> preAuthChain,
            MiddlewareChain<AuthedDTO, PipelineException> postAuthChain,
            AuthBridge authBridge) {
        this.preAuthChain = preAuthChain;
        this.postAuthChain = postAuthChain;
        this.authBridge = authBridge;
    }

    @Override
    public void execute(UnAuthedDTO config, Controller controller) throws PipelineException {
        preAuthChain.execute(config, unAuthed -> {
                Session session = authBridge.authenticate(unAuthed);
                AuthedDTO authed = new AuthedDTO(
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
