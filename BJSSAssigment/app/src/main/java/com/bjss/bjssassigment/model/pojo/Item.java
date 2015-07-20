package com.bjss.bjssassigment.model.pojo;

import com.bjss.bjssassigment.model.constant.Constant;

/**
 * Created by Filippo-TheAppExpert on 7/17/2015.
 */
public class Item implements Comparable<Item> {

    public String mItemName;
    public double mPrice;
    public int mQuantity;
    public Constant.ItemType mItemType;
    public int mIcon;

    public Item(Constant.ItemType itemType, String itemName, double price, int quantity, int icon) {
        mItemType = itemType;
        mItemName = itemName;
        mPrice = price;
        mQuantity = quantity;
        mIcon = icon;
    }

    @Override
    public int compareTo(Item item) {
        return mItemType.compareTo(item.mItemType);
    }

    public void addQuantity(int quantity){
        mQuantity += quantity;
    }

    public void subQuantity(int quantity){
        mQuantity -= quantity;
    }
}
