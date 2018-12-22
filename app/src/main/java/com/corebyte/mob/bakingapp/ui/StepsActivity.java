package com.corebyte.mob.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Ingredient;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;
import com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment;
import com.corebyte.mob.bakingapp.ui.fragment.StepMasterFragment;
import com.corebyte.mob.bakingapp.utils.RecipeUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


import org.w3c.dom.Text;

import java.util.List;

import static com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment.INGREDIENT_KEY;

public class StepsActivity extends AppCompatActivity {

    public static final String TAG = StepsActivity.class.getSimpleName();

    public static final String RECIPE_KEY = "RECIPE_KEY";

    private Recipe recipe;
    private boolean dualPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (getIntent().hasExtra(RECIPE_KEY)) {
            Bundle bundle = getIntent().getExtras();
            recipe = (Recipe) bundle.getParcelable(RECIPE_KEY);
            setTitle(recipe.getName().toUpperCase());
        }

        StepMasterFragment stepMasterFragment = null;

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.stepFrameLayout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //portrait
        if (frameLayout != null) {

            dualPanel = false;

            stepMasterFragment = (StepMasterFragment)fragmentManager
                    .findFragmentByTag("MASTER");

            if(stepMasterFragment == null) {
                stepMasterFragment = new StepMasterFragment();

                //bundle
                Bundle bundle = new Bundle();
                bundle.putParcelable(RECIPE_KEY, recipe);
                stepMasterFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.stepFrameLayout, stepMasterFragment);

            }

            StepDetailFragment stepDetailFragment = (StepDetailFragment)fragmentManager
                    .findFragmentById(R.id.detailFrameLayout);
            if (stepDetailFragment != null) {
                fragmentTransaction.remove(stepDetailFragment);
            }

            fragmentTransaction.commit();

        } else {
            //landscape
            dualPanel = true;
            stepMasterFragment = (StepMasterFragment) fragmentManager
                    .findFragmentById(R.id.masterFrameLayout);

            if (stepMasterFragment == null) {
                stepMasterFragment = new StepMasterFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable(RECIPE_KEY, recipe);
                stepMasterFragment.setArguments(bundle);

                fragmentTransaction.add(R.id.masterFrameLayout, stepMasterFragment);
            }

            StepDetailFragment stepDetailFragment = (StepDetailFragment)fragmentManager
                    .findFragmentById(R.id.detailFrameLayout);

            if(stepDetailFragment == null) {
                stepDetailFragment = new StepDetailFragment();
                fragmentTransaction.add(R.id.detailFrameLayout, stepDetailFragment);
            }

            fragmentTransaction.commit();

        }

        stepMasterFragment.setOnStepMasterSelectedListener(new StepMasterFragment.onStepMasterSelectedListener() {
            @Override
            public void onItemSelected(Step step) {
                displayRecipeStepDetail(step);
            }
        });

    }

    private void displayRecipeStepDetail(Step step) {

        if (dualPanel) {

            StepDetailFragment stepDetailFragment = (StepDetailFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.detailFrameLayout);
            stepDetailFragment.displayRecipeStepDetail(step, RecipeUtil
                    .prepareIngredientText(recipe.getIngredients()) );

        } else {

            Bundle bundle = new Bundle();
            bundle.putParcelable(StepDetailFragment.STEP_KEY, step);
            bundle.putString(INGREDIENT_KEY,
                    RecipeUtil.prepareIngredientText(recipe.getIngredients()));

            StepDetailFragment detailFragment = new StepDetailFragment();
            detailFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.stepFrameLayout, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
