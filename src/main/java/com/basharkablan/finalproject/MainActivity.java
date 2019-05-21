package com.basharkablan.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ExitDialog.ExitListener {

    public static AppCompatActivity ea; // used for exit only

    public static final String mBroadcastItemAction = "com.basharkablan.finalproject.item";
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ea = this;

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastItemAction);

        Intent serviceIntent = new Intent(this, BroadcastService.class);
        startService(serviceIntent);
    }

    // Buttons handlers
    public void onClickMenBtn(View view) {
        Intent intent = new Intent(this, MenActivity.class);
        startActivity(intent);
    }

    public void onClickWomenBtn(View view) {
        Intent intent = new Intent(this, WomenActivity.class);
        startActivity(intent);
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
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.Exit_Id:
                new ExitDialog().show(getSupportFragmentManager(),"");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onExitPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(mBroadcastItemAction)) {
                Item item = (Item) intent.getSerializableExtra("Data");

                ItemDialog dialogFragment = new ItemDialog();
                Bundle bundle = new Bundle();
                bundle.putString("NameText", item.getName());
                bundle.putString("PriceText", item.getPrice());
                bundle.putString("ImageText", item.getImage());
                bundle.putString("DetailsText", item.getDetails());
                dialogFragment.setArguments(bundle);
                dialogFragment.show((MainActivity.this).getSupportFragmentManager(), "Item Dialog");

                Intent stopIntent = new Intent(MainActivity.this, BroadcastService.class);
                stopService(stopIntent);
            }
        }
    };

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }
}
