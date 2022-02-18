package co.wm21.https.api_request;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

public class JsonObjectResponse extends JsonResponse<JSONObject> {

    int method;
    String url;
    JSONObject jsonObject;
    Response.Listener<JSONObject> listener;
    Response.ErrorListener errorListener;
    AfterListener afterListener;
    BeforeListener beforeListener;
    Object returnThing;

    public JsonObjectResponse(int method, String url, JSONObject jsonObject) {
        this.method = method;
        this.url = url;
        this.jsonObject = jsonObject;
    }

    public JsonObjectResponse success(Response.Listener<JSONObject> listener) {
        this.listener = listener;
        return this;
    }

    public JsonObjectResponse success(Listener<JSONObject> listener) {
        this.listener = response -> returnThing = listener.onResponse(response);
        return this;
    }

    public JsonObjectResponse error(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public JsonObjectResponse before(BeforeListener beforeListener) {
        this.beforeListener = beforeListener;
        return this;
    }

    public JsonObjectResponse after(AfterListener afterListener) {
        this.afterListener = afterListener;
        return this;
    }

    @Override
    public JsonRequest<JSONObject> parseToJsonRequest() {
        if (listener == null)
            listener = response -> {};
        if (errorListener == null)
            errorListener = Throwable::printStackTrace;
        if (afterListener == null)
            afterListener = returnObj -> {};
        Response.Listener<JSONObject> finalListener = response -> {
            listener.onResponse(response);
            try {
                afterListener.onAfter(returnThing);
            } catch (Exception e) { e.printStackTrace(); }
        };
        return new JsonObjectRequest(method, url, jsonObject, finalListener, errorListener);
    }

    public BeforeListener getBefore() {
        if (beforeListener == null)
            beforeListener = obj -> {};
        return beforeListener;
    }
}
