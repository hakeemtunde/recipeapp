package com.corebyte.mob.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.StepsListAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;

import java.util.ArrayList;

import static com.corebyte.mob.bakingapp.ui.StepsActivity.RECIPE_KEY;
import static com.corebyte.mob.bakingapp.ui.fragment.StepDetailFragment.STEP_KEY;

public class StepMasterFragment extends ListFragment {

    private static final String TAG = StepMasterFragment.class.getSimpleName();

    private onStepMasterSelectedListener onStepMasterSelectedListener;
    private Recipe recipe;
    private ArrayList<Step> steps = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        steps = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            recipe = bundle.getParcelable(RECIPE_KEY);
            steps = recipe.getSteps();
        }

        StepsListAdapter adapter = new StepsListAdapter(getContext(), steps);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                triggerClick(position);
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_KEY, steps);
        outState.putParcelable(RECIPE_KEY, recipe);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(STEP_KEY)) {
            steps = savedInstanceState.getParcelableArrayList(STEP_KEY);
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
        }
    }

    public void setOnStepMasterSelectedListener(onStepMasterSelectedListener listener) {
        onStepMasterSelectedListener = listener;
    }

    private void triggerClick(int position) {

        if (onStepMasterSelectedListener != null) {
            Step step = steps.get(position);
            onStepMasterSelectedListener.onItemSelected(step);
        }
    }

    public interface onStepMasterSelectedListener {
        public void onItemSelected(Step step);
    }

}
