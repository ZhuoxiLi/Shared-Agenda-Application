package response;

import javax.ws.rs.core.Response.Status;

public final class ResponseGenerator {
    public static <T> Response<T> ok(final T data) {
        return new Response<T>().withData(data).withStatusCode(Status.OK.getStatusCode());
    }

    public static <T> Response<T> badRequest() {
        return new Response<T>().withStatusCode(Status.BAD_REQUEST.getStatusCode());
    }
}
