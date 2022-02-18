package co.wm21.https.api_request;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonArrayResponse extends JsonResponse<JSONArray> {

    int method;
    String url;
    JSONObject jsonObject;
    Response.Listener<JSONArray> listener;
    Response.ErrorListener errorListener;
    AfterListener afterListener;
    BeforeListener beforeListener;
    Object returnThing;

    public JsonArrayResponse(int method, String url, JSONObject jsonObject) {
        this.method = method;
        this.url = url;
        this.jsonObject = jsonObject;
    }

    public JsonArrayResponse success(Response.Listener<JSONArray> listener) {
        this.listener = listener;
        return this;
    }

    public JsonArrayResponse success(Listener<JSONArray> listener) {
        this.listener = response -> returnThing = listener.onResponse(response);
        return this;
    }

    public JsonArrayResponse error(Response.ErrorListener errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    public JsonArrayResponse before(BeforeListener beforeListener) {
        this.beforeListener = beforeListener;
        return this;
    }

    public JsonArrayResponse after(AfterListener afterListener) {
        this.afterListener = afterListener;
        return this;
    }

    @Override
    public JsonRequest<JSONArray> parseToJsonRequest() {
        if (listener == null)
            listener = response -> {};
        if (errorListener == null)
            errorListener = Throwable::printStackTrace;
        if (afterListener == null)
            afterListener = returnObj -> {};
        Response.Listener<JSONArray> finalListener = response -> {
            listener.onResponse(response);
            try {
                afterListener.onAfter(returnThing);
            } catch (Exception e) { e.printStackTrace(); }
        };
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonArrayRequest(method, url, jsonArray, finalListener, errorListener);
    }

    public BeforeListener getBefore() {
        if (beforeListener == null)
            beforeListener = obj -> {};
        return beforeListener;
    }
}