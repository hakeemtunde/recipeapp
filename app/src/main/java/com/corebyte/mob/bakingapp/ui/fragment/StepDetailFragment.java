package com.corebyte.mob.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment {

    public static final String STEP_KEY = "STEP_KEY";
    public static final String INGREDIENT_KEY = "INGREDIENT_KEY";
    public static final String CURRENT_WINDOW_KEY = "CURRENT_WINDOW_KEY";
    public static final String PLAYBACK_POSITION_KEY = "PLAYBACK_POSITION_KEY";
    public static final String PLAY_WHEN_READY_KEY = "PLAY_WHEN_READY_KEY";
    private static final String TAG = StepDetailFragment.class.getSimpleName();

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    private TextView stepShortDescTv;
    private TextView stepFullDescTv;
    private TextView ingredientTv;

    private PlayerView playerView;
    private SimpleExoPlayer player;

    private Step step;
    private String ingredientTxt;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STEP_KEY)) {
            step = savedInstanceState.getParcelable(STEP_KEY);
            ingredientTxt = savedInstanceState.getString(INGREDIENT_KEY);

            if (step != null) {

                currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_KEY);
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);

                displayRecipeStepDetail(step, ingredientTxt);
                playVideo(step.getVideoUrl());
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepShortDescTv = (TextView) view.findViewById(R.id.step_short_desc_tv);
        stepFullDescTv = (TextView) view.findViewById(R.id.step_full_desc_tv);
        ingredientTv = (TextView) view.findViewById(R.id.step_ingredient_tv);
        playerView = view.findViewById(R.id.videoPlayer);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(STEP_KEY)) {
            step = bundle.getParcelable(STEP_KEY);
            ingredientTxt = bundle.getString(INGREDIENT_KEY);

            if (step != null) {
                initializePlayer();
                displayRecipeStepDetail(step, ingredientTxt);
            }

        }

    }

    public void displayRecipeStepDetail(Step step, String ingredientTxt) {

        if (stepShortDescTv == null || stepFullDescTv == null || ingredientTv == null) return;
        stepShortDescTv.setText("Short Description: " + step.getShortDescription());
        stepFullDescTv.setText(step.getDescription());
        ingredientTv.setText(ingredientTxt);

        boolean isEmptyUri = step.getVideoUrl().isEmpty();

        if (!isEmptyUri) {
            playVideo(step.getVideoUrl());
        }

        hideOrShowPlayer(isEmptyUri);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && player == null) {
            initializePlayer();
        }

        if (getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            step = getArguments().getParcelable(STEP_KEY);
            ingredientTxt = getArguments().getString(INGREDIENT_KEY);

            if (step != null) {
                displayRecipeStepDetail(step, ingredientTxt);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_KEY, step);
        outState.putString(INGREDIENT_KEY, ingredientTxt);
        outState.putInt(CURRENT_WINDOW_KEY, currentWindow);
        outState.putLong(PLAYBACK_POSITION_KEY, playbackPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, playWhenReady);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STEP_KEY)) {
            step = savedInstanceState.getParcelable(STEP_KEY);
            ingredientTxt = savedInstanceState.getString(INGREDIENT_KEY);

            Log.i(TAG, "onViewStateRestored");
            if (step != null) {

                currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_KEY);
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
                playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);

                displayRecipeStepDetail(step, ingredientTxt);
                playVideo(step.getVideoUrl());
            }

        }
    }

    private void initializePlayer() {
        TrackSelector trackSelection = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelection);
        playerView.setPlayer(player);
        player.setPlayWhenReady(true);
    }

    private void playVideo(String videoLink) {

        if (player == null) return;

        Uri videoUri = Uri.parse(videoLink);
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("Baking-app");
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
        player.prepare(mediaSource);

        if (playbackPosition != 0) {
            player.seekTo(currentWindow, playbackPosition);
        }

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
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


}
