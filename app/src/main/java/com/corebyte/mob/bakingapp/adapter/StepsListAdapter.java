package com.corebyte.mob.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Step;

import java.util.List;

public class StepsListAdapter extends ArrayAdapter<Step> {

    private Context context;
    private List<Step> steps;

    public StepsListAdapter(Context context, List<Step> steps) {
        super(context, 0, steps);

        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View stepListView = convertView;

        if (stepListView == null) {
            stepListView = LayoutInflater.from(context)
            .inflate(R.layout.steps_list_item, parent, false);

            TextView stepShortDescTv = (TextView) stepListView.findViewById(R.id.step_heading_tv);

            Step step = steps.get(position);

            stepShortDescTv.setText(step.getShortDescription());

        }

        return stepListView;
    }
}
