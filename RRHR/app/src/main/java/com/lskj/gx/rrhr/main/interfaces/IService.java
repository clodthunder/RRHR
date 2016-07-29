package com.lskj.gx.rrhr.main.interfaces;

import com.lskj.gx.rrhr.main.bean.CResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Home on 16/7/24.
 */
public interface IService {

    /**
     * 请求movie数据
     *
     * @return
     */

    @GET("top250")
    Observable<CResponse> getMovie(@Query("start") int start, @Query("count") int count);
}
