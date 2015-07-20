package com.bjss.bjssassigment.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.presenter.CheckoutPresenter;
import com.bjss.bjssassigment.view.activity.MainActivity;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class CheckoutFragment extends Fragment implements CheckoutPresenter.CheckoutViewListener {

    private CheckoutPresenter mPresenter;
    private Spinner mCurrencies;
    private ArrayAdapter<String> mAdapter;
    private ShoppingCartFragment.ShoppingCartListener mListener;
    private TextView mTotalItems, mTotalDue, mCurrencyType, mCurrencyStandFor;
    private Button mPay;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CheckoutPresenter(CheckoutFragment.this);
        mPresenter.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        configViews(view);
        setRetainInstance(true);
        configProgressDialog();
        return view;
    }

    private void configProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle("Getting Exchange Rates");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mListener = (ShoppingCartFragment.ShoppingCartListener) getActivity();
        }
    }

    private void configViews(View view) {
        mCurrencies = (Spinner) view.findViewById(R.id.currencies);
        mTotalItems = (TextView) view.findViewById(R.id.totalItems);
        mTotalDue = (TextView) view.findViewById(R.id.totalDue);
        mCurrencyType = (TextView) view.findViewById(R.id.currencyType);
        mCurrencyStandFor = (TextView) view.findViewById(R.id.currencyStandFor);
        mPay = (Button) view.findViewById(R.id.pay);

        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Payed Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        mCurrencies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mPresenter.convertCurrencyInto();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mPresenter.getCurrencies();
            if(mTotalItems != null && mTotalDue != null) {
                mTotalItems.setText(Double.toString(mPresenter.getItemTotal()));
                mTotalDue.setText(Double.toString(mPresenter.getItemTotal()));
            }
        }
    }

    @Override
    public void onCurrencyList(List<String> currencies) {

        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, currencies);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = mAdapter.getPosition("GBP");

        if (mCurrencies != null) {
            mCurrencies.setAdapter(mAdapter);
            mCurrencies.setSelection(position, true);
        }
        mListener.notifyPagerAdapter();
    }

    @Override
    public void onExchangeRate(double price, String currency, String currencyStandFor) {
        mCurrencyStandFor.setText(currencyStandFor);
        mTotalDue.setText(Double.toString(Double.valueOf(new DecimalFormat("#.##").format(price))));
        mCurrencyType.setText("[" + currency + "]");
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), "Error Occurred! " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getCurrency() {
        return (String) mCurrencies.getSelectedItem();
    }
}