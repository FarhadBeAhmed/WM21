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
