package com.bjss.bjssassigment.presenter.api;
import com.bjss.bjssassigment.model.constant.Constant;
import com.bjss.bjssassigment.presenter.api.callback.CurrencyApi;
import com.bjss.bjssassigment.presenter.api.callback.ExchangeRateApi;
import com.bjss.bjssassigment.presenter.api.gson.StringDeserializer;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Filippo-TheAppExpert on 6/4/2015.
 */
public class ApiManager {

    private CurrencyApi mCurrencyApi;
    private ExchangeRateApi mExchangeRateApi;

    public CurrencyApi getCurrencyApi() {
        if (mCurrencyApi == null) {
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(String.class, new StringDeserializer());


            mCurrencyApi = new RestAdapter.Builder()
                    .setEndpoint(Constant.CURRENCIES_URL)
                    .setConverter(new GsonConverter(gson.create()))
                    .build()
                    .create(CurrencyApi.class);
        }
        return mCurrencyApi;
    }

    public ExchangeRateApi getExchangeRateApi() {
        if (mExchangeRateApi == null) {
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(String.class, new StringDeserializer());


            mExchangeRateApi = new RestAdapter.Builder()
                    .setEndpoint(Constant.EXCHANGE_RATES_URL)
                    .setConverter(new GsonConverter(gson.create()))
                    .build()
                    .create(ExchangeRateApi.class);
        }
        return mExchangeRateApi;
    }
}
