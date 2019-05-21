package com.basharkablan.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WomenActivity extends AppCompatActivity implements ExitDialog.ExitListener {

    ArrayList<Item> data;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_women);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        ListView listView = findViewById(R.id.women_list);

        data = ItemXMLParser.parseItems(this,ItemXMLParser.WOMEN);
        final ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.row_item, data);

        if(sharedPref.getBoolean(getString(R.string.pref_key_save), false))
            for (int i=0; i < data.size(); i++)
                if (!sharedPref.getString(data.get(i).getName(), getString(R.string.not_found)).equals(getString(R.string.not_found))) {
                    data.remove(i);
                    itemAdapter.notifyDataSetChanged();
                    i--;
                }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                String itemName = data.get(index).getName();
                // Saving Data
                if(sharedPref.getBoolean(getString(R.string.pref_key_save), false))
                    sharedPref.edit().putString(itemName, itemName).apply();

                data.remove(index);
                itemAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), itemName + " Deleted", Toast.LENGTH_SHORT).show();
                return true;
            }});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemDialog dialogFragment = new ItemDialog();
                Bundle bundle = new Bundle();
                bundle.putString("NameText",data.get(position).getName());
                bundle.putString("PriceText",data.get(position).getPrice());
                bundle.putString("ImageText",data.get(position).getImage());
                bundle.putString("DetailsText", data.get(position).getDetails());
                dialogFragment.setArguments(bundle);
                dialogFragment.show((WomenActivity.this).getSupportFragmentManager(),"Item Dialog");
            }
        });

        listView.setAdapter(itemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SettingId:
                startActivityForResult(new Intent(this, SettingsActivity.class), 1);
                return true;
            case R.id.Exit_Id:
                new ExitDialog().show(getSupportFragmentManager(),"");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onExitPressed() {
        finish();
        MainActivity.ea.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1) {
            loadData();
        }
    }
}
