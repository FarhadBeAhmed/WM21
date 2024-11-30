/*
package co.wm21.https.FHelper.networks.Remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    public static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient(String bsURL) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(bsURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            if (!retrofit.baseUrl().equals(bsURL)) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(bsURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    private static Retrofit anotherRetrofit = null;

    public static Retrofit getAnotherClient(String baseUrl) {
        if (anotherRetrofit == null) {
            anotherRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return anotherRetrofit;
    }
}
*/
package co.wm21.https.FHelper.networks.Remote;

import java.util.concurrent.TimeUnit;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.NetworkInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static Retrofit retrofit = null;

    // Configure OkHttpClient with interceptors
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor.headerInterceptor) // Add custom header interceptor
            .addInterceptor(NetworkInterceptor.loggingInterceptor) // Add logging interceptor
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantValues.URL) // Use global base URL
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Method for another Retrofit instance if needed
    private static Retrofit anotherRetrofit = null;

    public static Retrofit getAnotherClient(String baseUrl) {
        if (anotherRetrofit == null) {
            anotherRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return anotherRetrofit;
    }
}
