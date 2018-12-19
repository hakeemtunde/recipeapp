package com.corebyte.mob.bakingapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.RecyclerViewAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.event.RecipeEventListener;
import com.corebyte.mob.bakingapp.utils.JsonParser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeEventListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recipe_list_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        recipes = JsonParser.getJsonParser().fetchRecipes(getApplicationContext());
        adapter = new RecyclerViewAdapter(getApplicationContext(), recipes, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        //launch step activities
        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra(StepsActivity.RECIPE_KEY, recipe);
        startActivity(intent);
    }
}
