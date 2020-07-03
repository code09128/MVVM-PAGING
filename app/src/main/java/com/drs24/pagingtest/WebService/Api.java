package com.drs24.pagingtest.WebService;

import com.drs24.pagingtest.Model.StackApiReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dustin0128 on 2020/3/18
 * https://api.stackexchange.com/2.2/answers?page=2&pagesize=50&site=stackoverflow
 */
public interface Api {

    /**
     * URL 帶入參數並且 query
     * @param page 頁數
     * @param size 資料比數
     * @param site 地點名稱
     * @return
     */
    @GET("answers")
    Call<StackApiReponse> getAnswers(
            @Query("page") int page,
            @Query("pagesize") int size,
            @Query("site") String site);
}
