package com.corebyte.mob.bakingapp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;

public class MainMasterFragment extends AbstractMasterFragment {

    public MainMasterFragment() {}

    @Override
    public void setLayoutManager() {
        if (getRecyclerView() != null) {
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
