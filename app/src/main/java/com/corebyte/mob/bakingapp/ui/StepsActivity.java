package com.corebyte.mob.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Recipe;

public class StepsActivity extends AppCompatActivity {

    public static final String TAG = StepsActivity.class.getSimpleName();

    public static final String RECIPE_KEY = "recipe";
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        setTitle("Testing");

        if (getIntent().hasExtra(RECIPE_KEY)) {
            Bundle bundle = getIntent().getExtras();
            recipe = (Recipe) bundle.getParcelable(RECIPE_KEY);
        }

        Log.d(TAG, "Name:----------"+recipe.toString());
    }


}
