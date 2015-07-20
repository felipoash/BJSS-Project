package com.bjss.bjssassigment.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.bjss.bjssassigment.model.callback.LifeCycleCallback;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/19/2015.
 */
public class ShoppingCartPresenter extends Presenter implements LifeCycleCallback {

    private ShoppingCartViewListener mListener;
    private List<Item> mItems;

    public ShoppingCartPresenter(ShoppingCartViewListener listener) {
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

    public void refreshItems() {
        mItems = mListener.getMainActivity().getAllItems();
        mListener.onReloadItems(mItems);
    }

    public interface ShoppingCartViewListener {

        MainActivity getMainActivity();

        void onReloadItems(List<Item> items);
    }
}
