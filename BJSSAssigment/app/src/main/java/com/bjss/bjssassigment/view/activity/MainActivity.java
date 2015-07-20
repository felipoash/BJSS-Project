package com.bjss.bjssassigment.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.model.adapter.ShoppingPagerAdapter;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.presenter.MainPresenter;
import com.bjss.bjssassigment.view.fragment.ShoppingCartFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainPresenter.MainViewListener, ShoppingCartFragment.ShoppingCartListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private ViewPager mPager;
    private ShoppingPagerAdapter mPagerAdapter;
    private MainPresenter mPresenter;
    private Button mCheckout;
    private TextView mCounter, mSubTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configActionBar();
        mPresenter = new MainPresenter(MainActivity.this);
        mPresenter.initialize();
        configPager();
        configViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void configActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("BJSS Shopping App");
        mToolbar.setSubtitle("Technical Assignment");
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
    }

    private void configPager() {
        mPager = (ViewPager) findViewById(R.id.vpPager);
        mPagerAdapter = new ShoppingPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private void configViews() {
        mCheckout = (Button) findViewById(R.id.checkout);
        mSubTotal = (TextView) findViewById(R.id.subTotal);
        RelativeLayout badgeLayout = (RelativeLayout) findViewById(R.id.badge);
        mCounter = (TextView) badgeLayout.findViewById(R.id.counter);
        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mPager.setCurrentItem(2);
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error... " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemAdded(Item item) {

        int totalCart = Integer.parseInt(mCounter.getText().toString());
        int quantity = item.mQuantity;

        mPresenter.addItem(item);
        mPresenter.incrementCounter(totalCart, quantity);
        mPresenter.processSubTotal();
    }

    @Override
    public void onItemRemoved(int position, int quantity) {
        int totalCart = Integer.parseInt(mCounter.getText().toString());

        mPresenter.onItemRemoved(position);
        mPresenter.decrementCounter(totalCart, quantity);
        mPresenter.processSubTotal();
    }

    @Override
    public void onQuantityIncreased(int quantity, Item item) {
        int totalCart = Integer.parseInt(mCounter.getText().toString());
        mPresenter.incrementItemByOne(item);
        mPresenter.incrementCounter(totalCart, 1);
        mPresenter.processSubTotal();
    }

    @Override
    public void onQuantityDecreased(int quantity, Item item) {
        int totalCart = Integer.parseInt(mCounter.getText().toString());
        mPresenter.decrementItemByOne(item);
        mPresenter.decrementCounter(totalCart, 1);
        mPresenter.processSubTotal();
    }

    @Override
    public void notifyPagerAdapter() {
        mPagerAdapter.notifyDataSetChanged();
        mPager.getAdapter().notifyDataSetChanged();
        mPager.invalidate();
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return MainActivity.this;
    }

    @Override
    public List<Item> getAllItems() {
        return mPresenter.getItems();
    }

    @Override
    public void onCounterChanged(int total) {
        mCounter.setText(Integer.toString(total));
    }

    @Override
    public void onSubTotalChanged(String subTotal) {
        mSubTotal.setText(subTotal);
    }
}
