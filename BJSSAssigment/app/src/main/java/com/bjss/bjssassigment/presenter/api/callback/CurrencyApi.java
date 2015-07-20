package com.bjss.bjssassigment.presenter.api.callback;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Filippo-TheAppExpert on 7/18/2015.
 */
public interface CurrencyApi {

    @GET("/currencies.json")
    void getCurrencyList(Callback<String> cb);
}
