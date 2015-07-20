package com.bjss.bjssassigment.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bjss.bjssassigment.model.callback.LifeCycleCallback;
import com.bjss.bjssassigment.model.pojo.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/19/2015.
 */
public class MainPresenter extends Presenter implements LifeCycleCallback {

    private MainViewListener mListener;
    private List<Item> mItems;

    public MainPresenter(MainViewListener listener) {
        mListener = listener;
    }

    @Override
    public void initialize() {
        mItems = new ArrayList<>();
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

    public List<Item> getItems() {
        return mItems;
    }

    public void addItem(Item item) {
        for (Item currentItem : mItems) {
            if (currentItem.mItemType.equals(item.mItemType)) {
                currentItem.addQuantity(item.mQuantity);
                return;
            }
        }
        mItems.add(item);
    }

    public void onItemRemoved(int position) {
        mItems.remove(position);
    }

    public void incrementCounter(int totalCart, int quantity) {
        int total = totalCart + quantity;
        mListener.onCounterChanged(total);
        Log.d("ADDDDD", "Add Here now " + quantity);
    }

    public void decrementCounter(int totalCart, int quantity) {
        int total = totalCart - quantity;
        mListener.onCounterChanged(total);
        Log.d("ADDDDD", "Decremment Here now " + quantity);
    }

    public void processSubTotal() {
        mListener.onSubTotalChanged(Double.toString(getSubTotal()));
    }

    private double getSubTotal() {
        double subTotal = 0.0;
        List<Item> items = getItems();

        for (Item item : items) {
            subTotal = subTotal + (item.mPrice * item.mQuantity);
        }

        return Double.valueOf(new DecimalFormat("#.##").format(subTotal));
    }

    public void incrementItemByOne(Item item) {
        for (Item currentItem : mItems) {
            if (currentItem.mItemType.equals(item.mItemType)) {
                currentItem.addQuantity(1);
                return;
            }
        }
        mItems.add(item);
    }

    public void decrementItemByOne(Item item) {
        for (Item currentItem : mItems) {
            if (currentItem.mItemType.equals(item.mItemType)) {
                currentItem.subQuantity(1);
                return;
            }
        }
        mItems.remove(item);
    }


    public interface MainViewListener {

        AppCompatActivity getAppCompatActivity();

        /**
         * This method is responsible for getting all list items inside the shopping cart
         *
         * @return List<Item>
         */
        List<Item> getAllItems();

        void onCounterChanged(int total);

        void onSubTotalChanged(String subTotal);
    }
}
