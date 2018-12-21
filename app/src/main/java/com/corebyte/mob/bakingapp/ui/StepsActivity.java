package com.corebyte.mob.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Ingredient;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;
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

    private PlayerView playerView;
    private SimpleExoPlayer player;

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

        playerView  = findViewById(R.id.videoPlayer);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((stepCount == (steps.size()-1))) return;

                stepCount++;
                displayRecipeStepDetail();


            }
        });

        initializePlayer();
        displayRecipeStepDetail();
    }

    private void displayRecipeStepDetail() {

        Step step = steps.get(stepCount);

        stepCountTv.setText("Step "+String.valueOf(stepCount+1));
        stepShortDescTv.setText(step.getShortDescription());
        stepFullDescTv.setText(step.getDescription());

        boolean isEmptyUri = step.getVideoUrl().isEmpty();

        if (!isEmptyUri) {
            playVideo(step.getVideoUrl());
        }

        hideOrShowPlayer(isEmptyUri);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void initializePlayer() {
        TrackSelector trackSelection = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelection);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
    }

    private void playVideo(String videoLink) {
        Uri videoUri =  Uri.parse(videoLink);
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Baking-app");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
        player.prepare(mediaSource);
    }

    private void hideOrShowPlayer(boolean isBlankLink) {
        if (!isBlankLink) {
            if (playerView.getVisibility() == View.INVISIBLE) {
                playerView.setVisibility(View.VISIBLE);
            }
        } else {
            playerView.setVisibility(View.INVISIBLE);
        }
    }


    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
