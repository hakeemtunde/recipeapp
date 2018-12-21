package com.corebyte.mob.bakingapp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;

public class MasterFragment extends AbstractMasterFragment {

    public MasterFragment() {}

    @Override
    public void setLayoutManager() {
        if (getRecyclerView() != null) {
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
