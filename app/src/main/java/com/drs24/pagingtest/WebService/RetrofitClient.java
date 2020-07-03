package com.drs24.pagingtest.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dustin0128 on 2020/3/18
 */
public class RetrofitClient {

    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    /***
     * 建立網路連接
     */
    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())// 使用 Gson 解析
                .build();
    }

    public static synchronized RetrofitClient getInsance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    /**請求 API 的 Client Service*/
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
