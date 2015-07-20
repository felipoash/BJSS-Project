package com.bjss.bjssassigment.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.model.adapter.ShoppingCartAdapter;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.presenter.ShoppingCartPresenter;
import com.bjss.bjssassigment.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class ShoppingCartFragment extends Fragment implements ShoppingCartPresenter.ShoppingCartViewListener, ShoppingCartAdapter.ShoppingAdapterListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Item> mItems = new ArrayList<>();
    private ShoppingCartListener mListener;
    private ShoppingCartPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ShoppingCartPresenter(ShoppingCartFragment.this);
        mPresenter.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        configViews(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mListener = (ShoppingCartListener) getActivity();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    private void configViews(View view) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.shoppinCart_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ShoppingCartAdapter(ShoppingCartFragment.this, mItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mPresenter.refreshItems();
        }
    }

    @Override
    public MainActivity getMainActivity() {
        return ((MainActivity) getActivity());
    }

    @Override
    public void onRemoveItem(int position, int quantity) {
        mListener.onItemRemoved(position, quantity);
        mItems.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onAddTap(int quantity, int position) {
        mListener.onQuantityIncreased(quantity, mItems.get(position));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDecTap(int quantity, int position) {
        mListener.onQuantityDecreased(quantity, mItems.get(position));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReloadItems(List<Item> items) {
        if (!mItems.isEmpty()) {
            mItems.clear();
        }
        mItems.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

    public interface ShoppingCartListener {

        void onItemAdded(Item item);

        void onItemRemoved(int position, int quantity);

        void onQuantityIncreased(int quantity, Item item);

        void onQuantityDecreased(int quantity, Item item);

        void notifyPagerAdapter();
    }
}