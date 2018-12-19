package com.corebyte.mob.bakingapp.event;

import com.corebyte.mob.bakingapp.entity.Recipe;

public interface RecipeEventListener {

    void onRecipeClicked(Recipe recipe);
}
