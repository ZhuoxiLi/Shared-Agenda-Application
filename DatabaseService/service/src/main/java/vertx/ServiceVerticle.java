package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import web.RouteBuilder;

@Slf4j
public class ServiceVerticle extends AbstractVerticle {

    public static final String LOCAL_HOST = "localhost";
    public static final String DEFAULT_HOST = "0.0.0.0";
    public static final int PORT = 8080;

    @Override
    public void start() {
        final Router router = new RouteBuilder(vertx).getRouter();

        final String prop = System.getenv("PORT");
        final int port = prop == null ? PORT : Integer.valueOf(prop);
        final String host = prop == null ? LOCAL_HOST : DEFAULT_HOST;

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(port, host, res -> {
                if (res.succeeded()) {
                    log.info("Listening on {}:{}", host, port);
                } else {
                    log.error("Fail to Listening on {}:{}", host, port);
                }
            });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
