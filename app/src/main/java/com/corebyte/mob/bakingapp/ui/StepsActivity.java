package com.corebyte.mob.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;
import com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment;
import com.corebyte.mob.bakingapp.ui.fragment.StepMasterFragment;
import com.corebyte.mob.bakingapp.utils.RecipeUtil;

import static com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment.INGREDIENT_KEY;
import static com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment.STEP_KEY;

public class StepsActivity extends AppCompatActivity {

    public static final String TAG = StepsActivity.class.getSimpleName();

    public static final String RECIPE_KEY = "RECIPE_KEY";
    StepMasterFragment stepMasterFragment;
    StepDetailFragment stepDetailFragment;
    private Recipe recipe;
    private Step step;
    private boolean dualPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable(STEP_KEY);
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        }

        if (getIntent().hasExtra(RECIPE_KEY) && recipe == null) {
            Bundle bundle = getIntent().getExtras();
            recipe = (Recipe) bundle.getParcelable(RECIPE_KEY);
            setTitle(recipe.getName().toUpperCase());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        stepMasterFragment = (StepMasterFragment) fragmentManager
                .findFragmentByTag("MASTER");

        if (stepMasterFragment == null) {
            stepMasterFragment = new StepMasterFragment();

            //bundle
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_KEY, recipe);
            stepMasterFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.masterFrameLayout, stepMasterFragment, "MASTER");
        }

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.detailFrameLayout);
        //landscape
        if (frameLayout != null) {
            dualPanel = true;
            stepDetailFragment = (StepDetailFragment) fragmentManager
                    .findFragmentByTag("DETAIL");

            if (stepDetailFragment == null) {
                stepDetailFragment = new StepDetailFragment();
                stepDetailFragment.setArguments(createStepDetailBundle());

                fragmentTransaction.add(R.id.detailFrameLayout, stepDetailFragment, "DETAIL");
            } else {
                //restore the last step
                fragmentTransaction.replace(R.id.detailFrameLayout, stepDetailFragment);
            }

            fragmentTransaction.replace(R.id.masterFrameLayout, stepMasterFragment);

        } else {
            //portrait
            dualPanel = false;
            stepDetailFragment = (StepDetailFragment) fragmentManager
                    .findFragmentByTag("DETAIL");
            if (stepDetailFragment != null) {
                stepDetailFragment.onPause();
                fragmentTransaction.remove(stepDetailFragment);
            }

        }

        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();

        stepMasterFragment.setOnStepMasterSelectedListener(new StepMasterFragment.onStepMasterSelectedListener() {
            @Override
            public void onItemSelected(Step step) {
                displayRecipeStepDetail(step);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_KEY, step);
        outState.putParcelable(RECIPE_KEY, recipe);
    }

    private void displayRecipeStepDetail(Step step) {
        this.step = step;
        Bundle bundle = createStepDetailBundle();
        StepDetailFragment stepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                .findFragmentByTag("DETAIL");

        if (stepDetailFragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.masterFrameLayout, stepDetailFragment);
            fragmentTransaction.commit();
        } else {
            stepDetailFragment.setArguments(bundle);
            stepDetailFragment.onStart();
        }
    }

    private Bundle createStepDetailBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepDetailFragment.STEP_KEY, step);
        bundle.putString(INGREDIENT_KEY,
                RecipeUtil.prepareIngredientText(recipe.getIngredients()));
        return bundle;
    }
}
