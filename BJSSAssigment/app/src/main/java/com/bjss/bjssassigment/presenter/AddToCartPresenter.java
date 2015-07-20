package com.bjss.bjssassigment.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.model.callback.LifeCycleCallback;
import com.bjss.bjssassigment.model.constant.Constant;
import com.bjss.bjssassigment.model.pojo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/19/2015.
 */
public class AddToCartPresenter extends Presenter implements LifeCycleCallback {

    private AddToCartListener mListener;

    public AddToCartPresenter(AddToCartListener listener) {
        mListener = listener;
    }

    @Override
    public void initialize() {

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

    public void showItemDialog(Constant.ItemType itemType) {
        switch (itemType) {
            case PEAS:
                mListener.showItemDialog(Constant.ItemType.PEAS, Constant.ItemType.PEAS.name(), Constant.ItemType.PEAS.getItemPrice(), getActivity().getString(R.string.peas), R.drawable.peas);
                break;
            case EGG:
                mListener.showItemDialog(Constant.ItemType.EGG, Constant.ItemType.EGG.name(), Constant.ItemType.EGG.getItemPrice(), getActivity().getString(R.string.egg), R.drawable.egg);
                break;
            case MILK:
                mListener.showItemDialog(Constant.ItemType.MILK, Constant.ItemType.MILK.name(), Constant.ItemType.MILK.getItemPrice(), getActivity().getString(R.string.milk), R.drawable.milk);
                break;
            case BEAN:
                mListener.showItemDialog(Constant.ItemType.BEAN, Constant.ItemType.BEAN.name(), Constant.ItemType.BEAN.getItemPrice(), getActivity().getString(R.string.bean), R.drawable.beans);
                break;
            default:
                mListener.onError("Item not found");
        }
    }

    private Activity getActivity() {
        return mListener.getMainActivity();
    }

    public List<Integer> getQuantityList() {

        List<Integer> list = new ArrayList<>();
        for(int i = 1 ; i <= 999; i++) {
            list.add(i);
        }
        return list;
    }

    public void addItem(Item item) {
        mListener.onItemAdd(item);
    }

    public interface AddToCartListener {

        void onItemAdd(Item item);

        Activity getMainActivity();

        void showItemDialog(Constant.ItemType itemType, String itemName, double price, String itemDescription, int itemIcon);

        void onError(String errorMsg);
    }
}
