package com.corebyte.mob.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class StepDetailFragment extends Fragment {

    public static final String STEP_KEY = "STEP_KEY";
    public static final String INGREDIENT_KEY = "INGREDIENT_KEY";

    private TextView stepShortDescTv;
    private TextView stepFullDescTv;
    private TextView ingredientTv;

    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepShortDescTv = (TextView)view.findViewById(R.id.step_short_desc_tv);
        stepFullDescTv = (TextView)view.findViewById(R.id.step_full_desc_tv);
        ingredientTv = (TextView)view.findViewById(R.id.step_ingredient_tv);
        playerView = view.findViewById(R.id.videoPlayer);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(STEP_KEY)) {
            Step step = bundle.getParcelable(STEP_KEY);
            String ingredientTxt = bundle.getString(INGREDIENT_KEY);

            initializePlayer();

            displayRecipeStepDetail(step, ingredientTxt);
        }

    }

    public void displayRecipeStepDetail(Step step, String ingredientTxt) {

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
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    private void initializePlayer() {
        TrackSelector trackSelection = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelection);
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
