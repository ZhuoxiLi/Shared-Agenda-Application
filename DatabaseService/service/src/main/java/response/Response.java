package response;

import lombok.Getter;

@Getter
public class Response<T> {
    private int statusCode;
    private String statusText = "";
    private T data;

    public Response<T> withData(final T data) {
        this.data = data;
        return this;
    }

    public Response<T> withStatusCode(final int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Response<T> withStatusText(final String statusText) {
        this.statusText = statusText;
        return this;
    }
}
