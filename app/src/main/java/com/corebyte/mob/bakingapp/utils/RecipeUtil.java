package com.corebyte.mob.bakingapp.utils;

import com.corebyte.mob.bakingapp.entity.Ingredient;

import java.util.List;

public class RecipeUtil {

    public static String RECIPE_URI = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static String RECIPES_KEY = "RECIPES_KEY";

    public static String prepareIngredientText(List<Ingredient> ingredients) {
        String ingredientStr = "";
        for (Ingredient ingredient : ingredients) {
            ingredientStr = ingredientStr + ingredient.getIngredient() + " Measure: " + ingredient.getMeasure()
                    + " Quantity: " + ingredient.getQuantity() +"\n";
        }

        return ingredientStr;
    }

}
