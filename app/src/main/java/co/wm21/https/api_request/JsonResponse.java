package co.wm21.https.api_request;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

public abstract class JsonResponse<T> {

    int method;
    String url;
    JSONObject jsonObject;
    Response.Listener<JSONObject> listener;
    Response.ErrorListener errorListener;
    AfterListener afterListener;
    BeforeListener beforeListener;
    Object returnThing;

    public abstract JsonResponse<T> success(Response.Listener<T> listener);

    public abstract JsonResponse<T> success(Listener<T> listener);

    public abstract JsonResponse<T> error(Response.ErrorListener errorListener);

    public abstract JsonResponse<T> before(BeforeListener beforeListener);

    public abstract JsonResponse<T> after(AfterListener afterListener);

    public abstract JsonRequest<T> parseToJsonRequest();

    public BeforeListener getBefore() {
        if (beforeListener == null)
            beforeListener = obj -> {};
        return beforeListener;
    }

    public interface Listener<T> {
        Object onResponse(T response);
    }

    public interface AfterListener {
        void onAfter(Object returnObj);
    }

    public interface BeforeListener {
        void onBefore(JSONObject obj);
    }
}
