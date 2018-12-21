package com.corebyte.mob.bakingapp.ui.fragment;

import android.support.v7.widget.GridLayoutManager;

public class MasterLandFragment extends AbstractMasterFragment {

    public MasterLandFragment() {}

    @Override
    public void setLayoutManager() {
        if (getRecyclerView() != null) {
            getRecyclerView().setLayoutManager( new GridLayoutManager(getActivity(), 2));
        }
    }


}
