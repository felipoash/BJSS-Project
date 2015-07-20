package com.bjss.bjssassigment.presenter.api.callback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Filippo-TheAppExpert on 7/19/2015.
 */
public interface ExchangeRateApi {

    @GET("/live")
    void getExchangeRates(@Query("access_key") String access_key,
                          @Query("source") String source, @Query("format") String format,
                          Callback<String> cb);
}
