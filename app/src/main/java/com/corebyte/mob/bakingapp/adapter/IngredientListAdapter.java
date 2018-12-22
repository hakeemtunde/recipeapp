package com.corebyte.mob.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Ingredient;

import java.util.List;

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private List<Ingredient> ingredients;

    public IngredientListAdapter(Context context, List<Ingredient> ingredients) {
        super(context,0, ingredients);

        this.context = context;
        this.ingredients = ingredients;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View ingredientItemView = convertView;

        if (ingredientItemView == null) {

            ingredientItemView = LayoutInflater.from(context)
                    .inflate(R.layout.ingredient_item_list, parent, false);

            Ingredient ingredient = ingredients.get(position);

            TextView ingredientTv = (TextView)ingredientItemView.findViewById(R.id.ingredient_tv);
            ingredientTv.setText(ingredient.getIngredient().toUpperCase());

            TextView measureTv = (TextView)ingredientItemView.findViewById(R.id.measure_tv);
            measureTv.setText( String.valueOf(ingredient.getQuantity()) + " " +ingredient.getMeasure());

        }

        return ingredientItemView;

    }
}
