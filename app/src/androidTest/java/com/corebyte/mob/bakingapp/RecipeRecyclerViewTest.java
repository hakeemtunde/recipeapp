package com.corebyte.mob.bakingapp;

import com.corebyte.mob.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class RecipeRecyclerViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIfRecipeRecyclerViewTest() {

        onView(withId(R.id.recipe_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }
}
