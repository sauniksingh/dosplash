package com.test.dosplash.data;

import android.content.Context;

import com.test.dosplash.data.api.ApiManager;
import com.test.dosplash.model.UnsplashImage;

import java.util.ArrayList;

import retrofit2.Call;

public class DataManager {

    private static DataManager instance;
    private ApiManager apiManager;

    private DataManager(Context context) {
    }

    /**
     * Returns the single instance of {@link DataManager} if
     * {@link #init(Context)} is called first
     *
     * @return instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call init() before getInstance()");
        }
        return instance;
    }

    /**
     * Method used to create an instance of {@link DataManager}
     *
     * @param context of the application passed from the {@link}
     * @return instance if it is null
     */
    public synchronized static DataManager init(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }
    /**
     * Method to initialize {@link ApiManager} class
     */
    public void initApiManager() {
        apiManager = ApiManager.getInstance();
    }

    public Call<ArrayList<UnsplashImage>> getImages(int page, int item) {
        return apiManager.getImages(page, item);
    }
}
