package com.basharkablan.finalproject;

import android.content.Context;
import android.content.res.AssetManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ItemXMLParser {

    public final static int MEN=0;
    public final static int WOMEN=1;

    private final static String KEY_ITEM="item";
    private final static String KEY_NAME="name";
    private final static String KEY_IMAGE="image";
    private final static String KEY_PRICE="price";
    private final static String KEY_DETAILS="details";

    public static ArrayList<Item> parseItems(Context context, int flag){
        ArrayList<Item> data = null;
        InputStream in;

        in=openItemsFile(context, flag);

        XmlPullParserFactory xmlFactoryObject;
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();

            parser.setInput(in, null);
            int eventType = parser.getEventType();
            Item currentItem = null;
            String inTag;
            String strTagText = null;

            while (eventType != XmlPullParser.END_DOCUMENT){
                inTag = parser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        data = new ArrayList<Item>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (inTag.equalsIgnoreCase(KEY_ITEM))
                            currentItem = new Item();
                        break;
                    case XmlPullParser.TEXT:
                        strTagText = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inTag.equalsIgnoreCase(KEY_ITEM))
                            data.add(currentItem);
                        else if (inTag.equalsIgnoreCase(KEY_NAME))
                            currentItem.setName(strTagText);
                        else if (inTag.equalsIgnoreCase(KEY_IMAGE))
                            currentItem.setImage(strTagText);
                        else if (inTag.equalsIgnoreCase(KEY_PRICE))
                            currentItem.setPrice(strTagText);
                        else if (inTag.equalsIgnoreCase(KEY_DETAILS))
                            currentItem.setDetails(strTagText);
                        inTag ="";
                        break;
                }//switch
                eventType = parser.next();
            }//while
        } catch (Exception e) {e.printStackTrace();}
        return data;
    }



    private static InputStream openItemsFile(Context context, int flag) {
        AssetManager assetManager = context.getAssets();
        InputStream in =null;
        try {
            if(flag == MEN)
                in = assetManager.open("men_items.xml");
            else
                in = assetManager.open("women_items.xml");
        } catch (IOException e) {e.printStackTrace();}
        return in;
    }
}
