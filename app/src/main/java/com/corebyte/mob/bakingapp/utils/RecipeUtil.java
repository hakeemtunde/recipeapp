package com.corebyte.mob.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.corebyte.mob.bakingapp.entity.Ingredient;
import com.corebyte.mob.bakingapp.entity.Recipe;

import java.util.List;

public class RecipeUtil {

    public static final String PREF_RECIPE = "PREF_RECIPE";
    public static final String PREF_KEY = "PREF_KEY";
    private static final String TAG = RecipeUtil.class.getSimpleName();
    public static String RECIPE_URI = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static String RECIPES_KEY = "RECIPES_KEY";

    public static String prepareIngredientText(List<Ingredient> ingredients) {
        String ingredientStr = "";
        for (Ingredient ingredient : ingredients) {
            ingredientStr = ingredientStr + ingredient.getIngredient() + " Measure: " + ingredient.getMeasure()
                    + " Quantity: " + ingredient.getQuantity() + "\n";
        }

        return ingredientStr;
    }

    public static void storeRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREF_KEY, Context.MODE_PRIVATE).edit();
        String recipeSerialized = recipe.serialize();
        editor.putString(PREF_RECIPE, recipeSerialized).apply();
    }

    public static Recipe retriveRecipe(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, context.MODE_PRIVATE);
        String recipeStr = preferences.getString(PREF_RECIPE, null);
        if (recipeStr == null) return null;
        return Recipe.deserialized(recipeStr);
    }

}
