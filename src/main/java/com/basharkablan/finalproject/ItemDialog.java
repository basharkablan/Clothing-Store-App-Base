package com.basharkablan.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemDialog extends AppCompatDialogFragment{

    public ImageView item_image;
    public TextView item_name;
    public TextView item_details;
    public TextView item_price;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_item,null);

        Bundle bundle = getArguments();

        String name = bundle.getString("NameText","");
        String price = bundle.getString("PriceText","");
        String image = bundle.getString("ImageText","");
        String details = bundle.getString("DetailsText", "");

        builder.setView(v).setTitle("Item: " + name).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        item_name = (TextView) v.findViewById(R.id.item_name);
        item_details = (TextView) v.findViewById(R.id.item_details);
        item_price = (TextView) v.findViewById(R.id.item_price);
        item_image = (ImageView) v.findViewById(R.id.item_image);

        int imageId = this.getResources().getIdentifier(image,"drawable", this.getContext().getPackageName());
        item_image.setImageResource(imageId);

        item_name.setText(name);
        item_details.setText(details);
        item_price.setText(price);

        builder.setView(v);
        return builder.create();
    }
}
