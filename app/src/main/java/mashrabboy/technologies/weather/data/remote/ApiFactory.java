package mashrabboy.technologies.weather.data.remote;


import androidx.annotation.NonNull;


import java.util.concurrent.TimeUnit;

import mashrabboy.technologies.weather.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiFactory {

    private static OkHttpClient sClient;
    private static volatile ApiClient mApiClient;

    @NonNull
    public static ApiClient getApiClient() {
        ApiClient service = mApiClient;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = mApiClient;
                if (service == null) {
                    service = mApiClient = buildTaxiRetrofit().create(ApiClient.class);
                }
            }
        }
        return service;
    }

    @NonNull
    private static Retrofit buildTaxiRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(getClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        Interceptor interceptor = chain -> {
            Request request = chain.request();
            request.newBuilder().addHeader("Cache-Control", "no-cache");
            return chain.proceed(request);
        };
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60 / 2, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(null)
                .build();
    }

}
