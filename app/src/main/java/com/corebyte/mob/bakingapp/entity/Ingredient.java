package com.corebyte.mob.bakingapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable{

    @SerializedName("quantity")
    private float mQuantity;
    @SerializedName("measure")
    private String mMeasure;
    @SerializedName("ingredient")
    private String mIngredient;

    public Ingredient() {}

    public float getQuantity() {
        return mQuantity;
    }

    public void setQuantity(float mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }

    public static Parcelable.Creator CREATOR = new Parcelable.Creator<Ingredient>(){
                @Override
                public Ingredient createFromParcel(Parcel parcel) {
                    return new Ingredient(parcel);
                }

                @Override
                public Ingredient[] newArray(int size) {
                    return new Ingredient[size];
                }
    };

    public Ingredient(Parcel parcel) {
        this.mIngredient = parcel.readString();
        this.mMeasure = parcel.readString();
        this.mQuantity = parcel.readFloat();
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "mQuantity=" + mQuantity +
                ", mMeasure='" + mMeasure + '\'' +
                ", mIngredient='" + mIngredient + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mIngredient);
        parcel.writeString(mMeasure);
        parcel.writeFloat(mQuantity);
    }
}
