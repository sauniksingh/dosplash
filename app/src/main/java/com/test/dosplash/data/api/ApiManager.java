package com.test.dosplash.data.api;

import com.test.dosplash.BuildConfig;
import com.test.dosplash.model.UnsplashImage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by appinventiv on 27/3/18.
 */

public class ApiManager {

    private static final ApiManager instance = new ApiManager();
    private OkHttpClient.Builder basicHttpClient;
    private ApiInterface apiClient;

    private ApiManager() {
        basicHttpClient = getBasicHttpClient();
        apiClient = getRetrofitService();
    }

    public static ApiManager getInstance() {
        return instance;
    }

    private ApiInterface getRetrofitService() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.api)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retrofitBuilder.client(basicHttpClient.build()).build();
        return retrofit.create(ApiInterface.class);

    }

    private static OkHttpClient.Builder getBasicHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(20000, TimeUnit.MILLISECONDS);
    }

    public Call<ArrayList<UnsplashImage>> getImages(int page, int item) {
        return apiClient.getImages(BuildConfig.accessKey, page, item);
    }
}
