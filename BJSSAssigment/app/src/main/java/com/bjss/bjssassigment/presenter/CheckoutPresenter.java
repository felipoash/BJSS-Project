package com.bjss.bjssassigment.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bjss.bjssassigment.model.callback.LifeCycleCallback;
import com.bjss.bjssassigment.model.constant.Constant;
import com.bjss.bjssassigment.model.pojo.Currency;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.model.utilities.SharedPreferenceUtils;
import com.bjss.bjssassigment.presenter.api.ApiManager;
import com.bjss.bjssassigment.presenter.api.callback.CurrencyApi;
import com.bjss.bjssassigment.presenter.api.callback.ExchangeRateApi;
import com.bjss.bjssassigment.view.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Filippo-TheAppExpert on 7/19/2015.
 */
public class CheckoutPresenter extends Presenter implements LifeCycleCallback {

    public static final String CURRENCIES = "currencies";
    private CheckoutViewListener mListener;
    private ApiManager mApiManager;

    public CheckoutPresenter(CheckoutViewListener listener) {
        mListener = listener;
    }

    @Override
    public void initialize() {
        mApiManager = new ApiManager();
    }

    private List<String> getCurrencyList(JSONObject json) {

        List<String> currencyList = new ArrayList<>();
        Iterator<String> currencyIterator = json.keys();

        while (currencyIterator.hasNext()) {
            String key = currencyIterator.next();
            String value = null;
            try {
                value = (String) json.get(key);
            } catch (JSONException e) {
            }
            currencyList.add(key);
        }
        return currencyList;
    }

    private void saveCurrenciesJSON(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mListener != null)
                SharedPreferenceUtils.save(mListener.getMainActivity(), CURRENCIES, s);
            }
        }).start();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityCreated(@Nullable Activity activity) {

    }

    public void getCurrencies() {

        CurrencyApi currencies = mApiManager.getCurrencyApi();
        currencies.getCurrencyList(new Callback<String>() {
            @Override
            public void success(String currencies, Response response) {

                try {
                    JSONObject json = new JSONObject(currencies);
                    saveCurrenciesJSON(json.toString());

                    List<String> currencyList = getCurrencyList(json);
                    if (mListener != null) {
                        mListener.onCurrencyList(currencyList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

                String savedJSON = SharedPreferenceUtils.getValue(mListener.getMainActivity(), CURRENCIES);
                if (savedJSON != null) {
                    try {
                        List<String> currencyList = getCurrencyList(new JSONObject(savedJSON));
                        if (currencyList != null) {
                            if(mListener != null)
                                mListener.onCurrencyList(currencyList);
                        } else {
                            if(mListener != null)
                                mListener.onError(error.getMessage());
                        }
                    } catch (JSONException e) {
                        if(mListener != null)
                            mListener.onError(e.getMessage());
                    }
                } else {
                    if(mListener != null)
                        mListener.onError(error.getMessage());
                }
            }
        });
    }

    public void convertCurrencyInto() {

        String currency = null;
        if(mListener != null) {
            currency = mListener.getCurrency();
        }


        if(mListener != null)
        mListener.showProgressDialog();

        ExchangeRateApi exchangeRates = mApiManager.getExchangeRateApi();

        if ("GBP".equals(currency)) {
            if(mListener != null) {
                mListener.onExchangeRate(getItemTotal(), currency, currencyStandFor(currency));
                mListener.hideProgressDialog();
            }
        } else {
            final String finalCurrency = currency;
            exchangeRates.getExchangeRates(Constant.ACCESS_KEY, Constant.SOURCE, Constant.FORMAT, new Callback<String>() {
                @Override
                public void success(String s, Response response) {

                    double gbpusd = 0, converted = 0;

                    try {
                        JSONObject json = new JSONObject(s);

                        if (json.getBoolean("success")) {

                            JSONObject ratesJson = json.getJSONObject("quotes");

                            Iterator<String> ratesIterator = ratesJson.keys();

                            while (ratesIterator.hasNext()) {
                                String key = ratesIterator.next();

                                String currencyToConvert = key.substring(3, key.length());

                                if (finalCurrency.equals(currencyToConvert)) {
                                    currencyToConvert = "USD" + currencyToConvert;
                                    Object val = getValue(ratesJson, currencyToConvert);
                                    converted = (double) val;
                                }

                                if ("USDGBP".equals(key)) {
                                    Object val = getValue(ratesJson, key);
                                    gbpusd = (double) val;
                                    gbpusd = (getItemTotal() / gbpusd);
                                }
                            }
                        } else {
                            if(mListener != null) {
                                mListener.onError("Exchange Rate Error " + response.getUrl());
                                mListener.hideProgressDialog();
                            }
                        }

                        double totalExchange = gbpusd * converted;
                        if(mListener != null) {
                            mListener.onExchangeRate(totalExchange, finalCurrency, currencyStandFor(finalCurrency));
                            mListener.hideProgressDialog();
                        }

                    } catch (JSONException e) {
                        if(mListener != null) {
                            mListener.onError("Exchange Rate Error " + e.getMessage());
                            mListener.hideProgressDialog();
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if(mListener != null) {
                        mListener.onError("Exchange Rate Error " + error.getMessage());
                        mListener.hideProgressDialog();
                    }
                }
            });
        }
    }

    private String currencyStandFor(String currency) {

        String savedJSON = null;
        if(mListener != null) {
            savedJSON = SharedPreferenceUtils.getValue(mListener.getMainActivity(), CURRENCIES);
        }
        try {

            JSONObject json = new JSONObject(savedJSON);
            Iterator<String> currencyIterator = json.keys();

            while (currencyIterator.hasNext()) {
                String key = currencyIterator.next();
                if (currency.equals(key)) {
                    String value = null;
                    try {
                        value = (String) json.get(key);
                    } catch (JSONException e) {
                    }
                    return value;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getValue(JSONObject ratesJson, String key) {
        Object value = null;
        try {
            value = ratesJson.get(key);
        } catch (JSONException e) {
            if(mListener != null) {
                mListener.onError("Exchange Rate Error " + e.getMessage());
            }
        }
        return value;
    }

    public double getItemTotal() {

        double total = 0.0;
        if(mListener != null) {
            List<Item> items = mListener.getMainActivity().getAllItems();
            for (Item item : items) {
                total = total + (item.mPrice * item.mQuantity);
            }
            total = Double.valueOf(new DecimalFormat("#.##").format(total));
        }
        return total;
    }

    public interface CheckoutViewListener {

        void onCurrencyList(List<String> currencies);

        void onExchangeRate(double price, String currency, String currencyStandFor);

        void onError(String message);

        MainActivity getMainActivity();

        void showProgressDialog();

        void hideProgressDialog();

        double getPrice();

        String getCurrency();
    }
}
