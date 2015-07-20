package com.bjss.bjssassigment.model.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bjss.bjssassigment.R;


/**
 * Created by Filippo-TheAppExpert on 7/16/2015.
 */
public class QuantityPicker extends LinearLayout implements View.OnClickListener, TextWatcher {

    private EditText mQuantity;
    private Button mIncrement, mDecrement;
    private TapListener mListener;

    public QuantityPicker(Context context) {
        super(context);
        inflateLayout();
    }

    public QuantityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateLayout();
    }

    public QuantityPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.quantity_picker, this);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        bindViews();
    }

    public void setListener(TapListener listener) {
        mListener = listener;
    }

    private void bindViews() {
        mQuantity = (EditText) findViewById(R.id.item_quantity);
        mQuantity.setCursorVisible(false);
        mQuantity.addTextChangedListener(this);
        mIncrement = (Button) findViewById(R.id.inc_number);
        mDecrement = (Button) findViewById(R.id.dec_number);
        mIncrement.setOnClickListener(this);
        mDecrement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inc_number:
                incrementQuantity();
                break;
            case R.id.dec_number:
                decrementQuantity();
                break;
            default:
                break;
        }
    }

    private void incrementQuantity() {
        int quantity = Integer.parseInt(mQuantity.getText().toString());
        quantity = quantity + 1;
        mQuantity.setText("" + quantity);
        if (mListener != null) {
            mListener.onAddTap(quantity);
        }
    }


    private void decrementQuantity() {
        int quantity = Integer.parseInt(mQuantity.getText().toString());

        if (quantity > 0) {
            quantity = quantity - 1;
            mQuantity.setText("" + quantity);
        }
        if (mListener != null) {
            mListener.onSubTap(quantity);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int quantity = Integer.parseInt(mQuantity.getText().toString());
        if (quantity < 0) {
            mQuantity.setText("0");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void setQuantity(int quantity) {
        mQuantity.setText("" + quantity);
    }

    public int getQuantity() {
        return Integer.parseInt(mQuantity.getText().toString());
    }

    public interface TapListener {

        void onAddTap(int quantity);

        void onSubTap(int quantity);
    }
}
