package com.corebyte.mob.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Ingredient;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;

import java.util.List;

public class StepsActivity extends AppCompatActivity {

    public static final String TAG = StepsActivity.class.getSimpleName();

    public static final String RECIPE_KEY = "RECIPE_KEY";
    private Recipe recipe;


    private TextView stepShortDescTv;
    private TextView stepFullDescTv;
    private TextView stepCountTv;
    private Button nextBtn;
    private Button prevBtn;

    private List<Step> steps;
    private  List<Ingredient> ingredients;
    private int stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (getIntent().hasExtra(RECIPE_KEY)) {
            Bundle bundle = getIntent().getExtras();
            recipe = (Recipe) bundle.getParcelable(RECIPE_KEY);

            setTitle(recipe.getName().toUpperCase());

            steps = recipe.getSteps();
            ingredients = recipe.getIngredients();
        }

        stepCount = 0;
        stepCountTv = (TextView)findViewById(R.id.step_counter_tv);
        stepShortDescTv = (TextView)findViewById(R.id.step_short_desc_tv);
        stepFullDescTv = (TextView)findViewById(R.id.step_full_desc_tv);
        nextBtn = (Button)findViewById(R.id.next_btn);
        prevBtn = (Button)findViewById(R.id.prev_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((stepCount == (steps.size()-1))) return;

                stepCount++;
                displayRecipeStepDetail();


            }
        });

        displayRecipeStepDetail();

//        Log.d(TAG, "Name:----------"+recipe.toString());
    }

    private void displayRecipeStepDetail() {

        Step step = steps.get(stepCount);

//                Log.d(TAG, "Name:----------"+steps.toString());

        Log.d(TAG, "Name:----------"+step.toString());
        stepCountTv.setText("Step "+String.valueOf(stepCount+1));
        stepShortDescTv.setText(step.getShortDescription());
        stepFullDescTv.setText(step.getDescription());

    }





}
