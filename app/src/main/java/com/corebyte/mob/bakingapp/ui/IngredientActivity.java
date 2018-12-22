package com.corebyte.mob.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.IngredientListAdapter;
import com.corebyte.mob.bakingapp.entity.Ingredient;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.utils.RecipeUtil;

import java.util.ArrayList;
import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    private ListView ingredientListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ingredientListView = (ListView) findViewById(R.id.ingredient_list);

        List<Ingredient> ingredients = new ArrayList<>();

        if (getIntent().hasExtra(StepsActivity.RECIPE_KEY)) {
            Bundle bundle = getIntent().getExtras();
            Recipe recipe = (Recipe) bundle.getParcelable(StepsActivity.RECIPE_KEY);

            setTitle(recipe.getName().toUpperCase() + " Recipe List");

            ingredients = recipe.getIngredients();
        }

        IngredientListAdapter adapter = new IngredientListAdapter(this, ingredients);
        ingredientListView.setAdapter(adapter);
    }
}
