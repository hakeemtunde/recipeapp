package com.corebyte.mob.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.utils.JsonParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonParser.getJsonParser().test(getApplicationContext());
    }
}
