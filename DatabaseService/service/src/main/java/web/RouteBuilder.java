package web;

import action.ActionHandler;
//import action.AddGuestHandler;
//import action.CreateRoomHandler;
//import action.GetScriptHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RouteBuilder {

    private Router router;

    public RouteBuilder(final Vertx vertx) {
        router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
//        Route("/get", new GetScriptHandler());
//        Route("/createroom", new CreateRoomHandler());
//        Route("/addguest", new AddGuestHandler());

        router.route().handler(GetCORSHandler());
    }

    private <Req, Resp> void Route(final String path, final ActionHandler<Req, Resp> action) {
        WebHandler<Req, Resp> webHandler = new WebHandler<>(action);
        router.route(path).handler(GetCORSHandler());
        router.route(path).handler(webHandler::handle);
    }

    private static Handler<RoutingContext> GetCORSHandler() {
        final Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add(HttpHeaders.CONTENT_TYPE.toString());
        allowedHeaders.add(HttpHeaders.ACCEPT.toString());
        allowedHeaders.add(HttpHeaders.AUTHORIZATION.toString());

        final Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);

        final CorsHandler handler = CorsHandler
            .create("*")
            .allowedHeaders(allowedHeaders)
            .allowedMethods(allowedMethods);
        return handler;
    }
}
