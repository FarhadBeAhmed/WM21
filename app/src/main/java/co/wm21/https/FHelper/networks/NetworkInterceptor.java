package co.wm21.https.FHelper.networks;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.MediaType;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

public class NetworkInterceptor {

    // Header Interceptor: Adds custom headers
    public static final Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer YOUR_TOKEN") // Add token or other headers
                    .addHeader("Content-Type", "application/json")
                    .build();

            return chain.proceed(modifiedRequest);
        }
    };

    // Logging Interceptor: Logs requests and responses
    public static final Interceptor loggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.nanoTime();

            // Log request details
            System.out.println(String.format("Request: %s %n%s", request.url(), request.headers()));
            if (request.body() != null) {
                Buffer buffer = new Buffer();
                request.body().writeTo(buffer);
                System.out.println("Request Body: " + buffer.readUtf8());
            }

            // Proceed with request
            Response response = chain.proceed(request);
            long endTime = System.nanoTime();

            // Log response details
            System.out.println(String.format(
                    "Response: %s %nTime: %.1fms %n%s",
                    response.request().url(),
                    (endTime - startTime) / 1e6d,
                    response.headers()
            ));

            // Log response body
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseBodyString = responseBody.string();
                System.out.println("Response Body: " + responseBodyString);

                // Rebuild response body to avoid consumption
                MediaType contentType = responseBody.contentType();
                responseBody = ResponseBody.create(contentType, responseBodyString);
                return response.newBuilder().body(responseBody).build();
            }

            return response;
        }
    };

    // Optional: Use OkHttp's built-in HttpLoggingInterceptor
    public static final HttpLoggingInterceptor simpleLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
}
