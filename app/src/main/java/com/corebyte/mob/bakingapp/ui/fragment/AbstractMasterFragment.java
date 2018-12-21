package com.corebyte.mob.bakingapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.RecyclerViewAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.event.RecipeEventListener;
import com.corebyte.mob.bakingapp.ui.StepsActivity;
import com.corebyte.mob.bakingapp.utils.JsonParser;

import java.util.List;

public abstract class AbstractMasterFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Recipe> recipes;

    public AbstractMasterFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recipe_list_rv);
        recyclerView.setHasFixedSize(true);
        setLayoutManager();

        recipes = JsonParser.getJsonParser().fetchRecipes(getContext());

        adapter = new RecyclerViewAdapter(getContext(), recipes, new RecipeEventListener() {
            @Override
            public void onRecipeClicked(Recipe recipe) {
                Intent intent = new Intent(getActivity(), StepsActivity.class);
                intent.putExtra(StepsActivity.RECIPE_KEY, recipe);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public abstract void setLayoutManager();

}
