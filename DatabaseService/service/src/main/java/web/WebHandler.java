package web;

import action.ActionHandler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import response.Response;
import response.ResponseGenerator;

import java.util.function.Consumer;

public class WebHandler<RequestT, ResponseT> {

    private ActionHandler<RequestT, ResponseT> actionHandler;

    public WebHandler(ActionHandler<RequestT, ResponseT> actionHandler) {
        this.actionHandler = actionHandler;
    }
    public void handle(final RoutingContext req) {
        RequestT reqData;

        try {
            reqData = Json.decodeValue(req.getBody(), actionHandler.getRequestType());
        } catch (final Exception e) {
            final Response<ResponseT> resp = ResponseGenerator.badRequest();
            response(req, resp, "Server could not understand request.");
            return;
        }


        try {
            final Consumer<Response<ResponseT>> toResponse = resp -> response(req, resp, Json.encode(resp.getData()));
            actionHandler.handle(reqData, toResponse);
        } catch (final Exception e) {
            final Response<ResponseT> resp = ResponseGenerator.badRequest();
            response(req, resp, "Server could not understand request.");
        }
    }

    private void response(final RoutingContext req, final Response<ResponseT> resp, final String respData) {
        req
            .response()
            .setStatusCode(resp.getStatusCode())
            .setStatusMessage(resp.getStatusText())
            .end(respData);
    }
}
