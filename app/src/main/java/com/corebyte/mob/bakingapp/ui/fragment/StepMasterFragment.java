package com.corebyte.mob.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;

import com.corebyte.mob.bakingapp.adapter.StepsListAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.entity.Step;
import com.corebyte.mob.bakingapp.ui.StepsActivity;

import java.util.ArrayList;
import java.util.List;

public class StepMasterFragment extends ListFragment {

    private onStepMasterSelectedListener onStepMasterSelectedListener;
    private List<Step> steps = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Recipe recipe = null;
        steps = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(StepsActivity.RECIPE_KEY) ) {
            recipe = bundle.getParcelable(StepsActivity.RECIPE_KEY);
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
