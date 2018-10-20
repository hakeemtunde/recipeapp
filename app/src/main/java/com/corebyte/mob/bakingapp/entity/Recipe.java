package com.corebyte.mob.bakingapp.entity;

import java.util.List;

public class Recipe {

    private int mId;
    private int mServings;
    private String mImage;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    public Recipe() {}

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }
}
