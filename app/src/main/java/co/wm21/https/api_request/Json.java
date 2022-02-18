package co.wm21.https.api_request;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import co.wm21.https.helpers.ProjectApp;

public class Json {

    private static final RequestQueue requestQueue = Volley.newRequestQueue(ProjectApp.getContext().getApplicationContext());

    @SafeVarargs
    public static <T> void addRequests(@NonNull Request<T>... requests) {
        for (Request<T> request : requests)
            requestQueue.add(request);
    }

    @SafeVarargs
    public static void addRequests(@NonNull JsonResponse... requests) {
        for (JsonResponse request : requests) {
            request.getBefore().onBefore(request.jsonObject);
            requestQueue.add(request.parseToJsonRequest());
        }
    }
}