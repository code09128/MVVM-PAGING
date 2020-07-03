package com.drs24.pagingtest.DataSource;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedListAdapter;

import com.drs24.pagingtest.Model.Item;
import com.drs24.pagingtest.Model.StackApiReponse;
import com.drs24.pagingtest.WebService.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dustin0128 on 2020/3/18
 * PageKeyedDataSource
 * 原始資料來源（遠端Server或其他資料來源）已有分頁功能，根據每頁的Key取得資料
 */
public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {

    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {
        //連接API參數設定
        RetrofitClient.getInsance()
                .getApi()
                .getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiReponse>() { //發出請求(異步)
                    @Override
                    public void onResponse(Call<StackApiReponse> call, Response<StackApiReponse> response) {
                        callback.onResult(response.body().items, null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onFailure(Call<StackApiReponse> call, Throwable t) {
                        //失敗
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiReponse>() { //發出請求(異步)
                    @Override
                    public void onResponse(Call<StackApiReponse> call, Response<StackApiReponse> response) {
                        if (response.body() != null) {
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().items, key);//下一頁 的Key

                            Log.e("before","=="+key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiReponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
                .enqueue(new Callback<StackApiReponse>() { //發出請求(異步)
                    @Override
                    public void onResponse(Call<StackApiReponse> call, Response<StackApiReponse> response) {
                        if (response.body() != null) {

                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body().items, key);//上一頁 的Key

                            Log.e("after","=="+key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiReponse> call, Throwable t) {

                    }
                });
    }
}
