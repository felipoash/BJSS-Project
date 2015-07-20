package com.bjss.bjssassigment.view.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.model.constant.Constant;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.model.widget.QuantityPicker;
import com.bjss.bjssassigment.presenter.AddToCartPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class AddToCartFragment extends Fragment implements AddToCartPresenter.AddToCartListener {

    private ImageView mAddPeas, mAddEgg, mAddMilk, mAddBean;
    private AddToCartPresenter mPresenter;
    private ShoppingCartFragment.ShoppingCartListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AddToCartPresenter(AddToCartFragment.this);
        mPresenter.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_items, container, false);
        configViews(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity != null) {
            mListener = (ShoppingCartFragment.ShoppingCartListener) getActivity();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void configViews(View view) {

        mAddPeas = (ImageView) view.findViewById(R.id.addPeas);
        mAddEgg = (ImageView) view.findViewById(R.id.addEgg);
        mAddMilk = (ImageView) view.findViewById(R.id.addMilk);
        mAddBean = (ImageView) view.findViewById(R.id.addBeans);

        mAddPeas.setOnClickListener(mClickListener);
        mAddEgg.setOnClickListener(mClickListener);
        mAddMilk.setOnClickListener(mClickListener);
        mAddBean.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addPeas:
                    mPresenter.showItemDialog(Constant.ItemType.PEAS);
                    break;
                case R.id.addEgg:
                    mPresenter.showItemDialog(Constant.ItemType.EGG);
                    break;
                case R.id.addMilk:
                    mPresenter.showItemDialog(Constant.ItemType.MILK);
                    break;
                case R.id.addBeans:
                    mPresenter.showItemDialog(Constant.ItemType.BEAN);
                    break;
            }
        }
    };


    @Override
    public void onItemAdd(Item item) {
        mListener.onItemAdded(item);
    }

    @Override
    public Activity getMainActivity() {
        return getActivity();
    }

    @Override
    public void showItemDialog(final Constant.ItemType itemType, final String itemName, final double price, String itemDescription, final int itemIcon) {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_to_cart_dialog, null);
        configDialogViews(dialogView, itemName, itemDescription);

        final Spinner quantitySpinner = (Spinner) dialogView.findViewById(R.id.item_quantity);
        List<Integer> quantityList = mPresenter.getQuantityList();


        ArrayAdapter<Integer> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, quantityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantitySpinner.setAdapter(adapter);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setTitle("Add Item (s)")
                .setIcon(R.mipmap.ic_add_icon)
                .setView(dialogView)
                .create();
        alertDialog.show();

        Button addToCart = (Button) dialogView.findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
                mPresenter.addItem(new Item(itemType, itemName, price, quantity, itemIcon));
                alertDialog.dismiss();
            }
        });
    }

    private void configDialogViews(View dialogView, String itemName, String itemDescription) {
        TextView title = (TextView) dialogView.findViewById(R.id.item_title);
        TextView description = (TextView) dialogView.findViewById(R.id.item_description);
        title.setText(itemName);
        description.setText(itemDescription);
    }

    @Override
    public void onError(String errorMsg) {
        Toast.makeText(getActivity(), "Error Occurred! " + errorMsg, Toast.LENGTH_SHORT).show();
    }
}