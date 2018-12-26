package com.corebyte.mob.bakingapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    public static Parcelable.Creator CREATOR = new Parcelable.Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    @SerializedName("id")
    private int mId;
    @SerializedName("servings")
    private int mServings;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private List<Ingredient> mIngredients;
    @SerializedName("steps")
    private ArrayList<Step> mSteps;

    public Recipe() {
    }

    public Recipe(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mServings = parcel.readInt();
        this.mImage = parcel.readString();
        this.mName = parcel.readString();

        this.mIngredients = new ArrayList<Ingredient>();
        parcel.readTypedList(this.mIngredients, Ingredient.CREATOR);

        this.mSteps = new ArrayList<Step>();
        parcel.readTypedList(this.mSteps, Step.CREATOR);
    }

    public static Recipe deserialized(String recipe) {
        Gson gson = new Gson();
        return gson.fromJson(recipe, Recipe.class);
    }

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

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "mId=" + mId +
                ", mServings=" + mServings +
                ", mImage='" + mImage + '\'' +
                ", mName='" + mName + '\'' +
                ", mIngredients=" + mIngredients +
                ", mSteps=" + mSteps +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mServings);
        parcel.writeString(this.mImage);
        parcel.writeString(this.mName);
        parcel.writeTypedList(this.mIngredients);
        parcel.writeTypedList(this.mSteps);
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);

    }
}
