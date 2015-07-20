package com.bjss.bjssassigment.model.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bjss.bjssassigment.view.fragment.AddToCartFragment;
import com.bjss.bjssassigment.view.fragment.CheckoutFragment;
import com.bjss.bjssassigment.view.fragment.ShoppingCartFragment;

/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class ShoppingPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_ITEMS = 3;
    private static final String TITLES[] = {"ITEMS", "SHOPPING CART", "CHECKOUT"};

    public ShoppingPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddToCartFragment();
            case 1:
                return new ShoppingCartFragment();
            case 2:
                return new CheckoutFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
