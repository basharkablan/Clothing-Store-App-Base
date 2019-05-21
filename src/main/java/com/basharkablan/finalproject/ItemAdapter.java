package com.basharkablan.finalproject;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context mContext;
    private int resourceid;
    private ArrayList<Item> itemsList;

    public ItemAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);

        this.mContext = context;
        this.resourceid = resource;
        this.itemsList = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String image = getItem(position).getImage();
        String price = getItem(position).getPrice();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(convertView == null)
            convertView = inflater.inflate(resourceid,parent,false);

        TextView nameText = convertView.findViewById(R.id.nameText);
        TextView priceText = convertView.findViewById(R.id.priceText);
        ImageView itemImage = convertView.findViewById(R.id.itemImage);

        nameText.setText(name);
        priceText.setText(price);

        int id = mContext.getResources().getIdentifier(image,"drawable", mContext.getPackageName());
        itemImage.setImageResource(id);

        return convertView;
    }
}
