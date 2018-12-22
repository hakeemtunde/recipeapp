package com.corebyte.mob.bakingapp.utils;

import com.corebyte.mob.bakingapp.entity.Ingredient;

import java.util.List;

public class RecipeUtil {

    public static String prepareIngredientText(List<Ingredient> ingredients) {
        String ingredientStr = "";
        for (Ingredient ingredient : ingredients) {
            ingredientStr = ingredientStr + ingredient.getIngredient() + " Measure: " + ingredient.getMeasure()
                    + " Quantity: " + ingredient.getQuantity() +"\n";
        }

        return ingredientStr;
    }

}
