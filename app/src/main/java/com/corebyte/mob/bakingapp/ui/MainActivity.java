package com.corebyte.mob.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.RecyclerViewAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.event.RecipeEventListener;
import com.corebyte.mob.bakingapp.ui.fragment.AbstractMasterFragment;
import com.corebyte.mob.bakingapp.ui.fragment.MasterFragment;
import com.corebyte.mob.bakingapp.ui.fragment.MasterLandFragment;
import com.corebyte.mob.bakingapp.utils.JsonParser;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private boolean mPortraitMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        AbstractMasterFragment masterFragment = null;

        int fragmentLayout = R.id.frameLayout;
        FrameLayout frameLayout = (FrameLayout)findViewById(fragmentLayout);
        if(frameLayout != null) {
            mPortraitMode = true;
        } else {
            mPortraitMode = false;
            fragmentLayout = R.id.frameLayoutLand;
        }


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        masterFragment = (AbstractMasterFragment) fragmentManager.findFragmentByTag("MASTER");

        if (masterFragment != null) {
            fragmentTransaction.remove(masterFragment);
        }

        if (mPortraitMode) {
            masterFragment = new MasterFragment();
        }else {
            masterFragment = new MasterLandFragment();
        }

        fragmentTransaction.add(fragmentLayout, masterFragment, "MASTER");
        fragmentTransaction.commit();


    }

}
