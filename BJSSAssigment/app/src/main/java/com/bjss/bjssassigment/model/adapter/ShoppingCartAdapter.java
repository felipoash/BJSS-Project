package com.bjss.bjssassigment.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjss.bjssassigment.R;
import com.bjss.bjssassigment.model.pojo.Item;
import com.bjss.bjssassigment.model.widget.QuantityPicker;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.Holder> {

    private static ShoppingAdapterListener mListener;
    private List<Item> mItems;

    public ShoppingCartAdapter(ShoppingAdapterListener listener, List<Item> items) {
        mListener = listener;
        mItems = items;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout, null);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        Item currentItem = mItems.get(position);

        holder.mItemIcon.setImageResource(currentItem.mIcon);

        int quantity = currentItem.mQuantity;
        holder.mQuantityPicker.setQuantity(quantity);

        double totalPrice = quantity * currentItem.mPrice;
        totalPrice = Double.valueOf(new DecimalFormat("#.##").format(totalPrice));

        if (totalPrice >= 1) {
            holder.mTotal.setText("£" + totalPrice);
        } else {
            holder.mTotal.setText(totalPrice + "p");
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private QuantityPicker mQuantityPicker;
        private ImageView mDelete;
        private ImageView mItemIcon;
        private TextView mTotal;

        public Holder(View v) {
            super(v);
            mItemIcon = (ImageView) v.findViewById(R.id.itemIcon);
            mTotal = (TextView) v.findViewById(R.id.liveItemTotal);
            mQuantityPicker = (QuantityPicker) v.findViewById(R.id.quantity);
            mDelete = (ImageView) v.findViewById(R.id.deleteItem);
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRemoveItem(getPosition(), mQuantityPicker.getQuantity());
                }
            });

            mQuantityPicker.setListener(new QuantityPicker.TapListener() {
                @Override
                public void onAddTap(int quantity) {
                    mListener.onAddTap(quantity, getPosition());
                }

                @Override
                public void onSubTap(int quantity) {
                    if (quantity == 0) {
                        mListener.onRemoveItem(getPosition(), 1);
                    } else {
                        mListener.onDecTap(quantity, getPosition());
                    }
                }
            });
        }
    }

    public interface ShoppingAdapterListener {

        void onRemoveItem(int position, int quantity);

        void onAddTap(int quantity, int position);

        void onDecTap(int quantity, int position);
    }
}
