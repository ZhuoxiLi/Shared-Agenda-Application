package action;

import response.Response;
import store.DataStore;

import java.util.function.Consumer;

public interface ActionHandler <RequestT, ResponseT> {
//    DataStore dataStore = DataStore.dataStore;
    DataStore dataStore = new DataStore();

    Class<RequestT> getRequestType();

    Class<ResponseT> getResponseType();

    void handle(RequestT req, Consumer<Response<ResponseT>>toResponse);
}
