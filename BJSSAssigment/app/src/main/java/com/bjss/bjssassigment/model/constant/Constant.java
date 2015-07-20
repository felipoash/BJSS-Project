package com.bjss.bjssassigment.model.constant;

/**
 * Created by Filippo-TheAppExpert on 7/16/2015.
 */
public class Constant {

    public static final String CURRENCIES_URL = "http://jsonrates.com/";
    public static final String ACCESS_KEY = "1ddbeb0a91f6724709b3475300855bb2";
    public static final String SOURCE = "USD";
    public static final String FORMAT = "1";
    public static final String EXCHANGE_RATES_URL = "http://apilayer.net/api/";

    public enum ItemType {
        PEAS(0.95), EGG(2.10), MILK(1.30), BEAN(0.73);
        double mItemPrice;

        ItemType(double price) {
            mItemPrice = price;
        }
        public double getItemPrice() {
            return mItemPrice;
        }
    }
}
