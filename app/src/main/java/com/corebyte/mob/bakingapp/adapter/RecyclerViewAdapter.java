package com.corebyte.mob.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.event.RecipeEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<Recipe> mRecipes;
    private Context mContext;
    private RecipeEventListener mListener;

    public RecyclerViewAdapter(Context context, List<Recipe> recipes, RecipeEventListener listener) {
        mRecipes = recipes;
        mContext = context;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        final Recipe recipe = mRecipes.get(position);
        Picasso.with(mContext).load(R.mipmap.img_recipe_foreground).into(viewHolder.imageView);
        viewHolder.recipeNameTv.setText(recipe.getName());
        viewHolder.servingsTv.setText(String.valueOf(recipe.getServings()));
        viewHolder.stepsTv.setText(String.valueOf(recipe.getSteps().size()));

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView recipeNameTv;
        TextView stepsTv;
        TextView servingsTv;

        ViewHolder(View view) {
            super(view);

            cardView = (CardView)view.findViewById(R.id.cv);
            imageView = (ImageView) view.findViewById(R.id.recipe_iv);
            recipeNameTv  = (TextView)view.findViewById(R.id.recipe_name_tv);
            stepsTv = (TextView)view.findViewById(R.id.steps_tv);
            servingsTv = (TextView)view.findViewById(R.id.serving_tv);

            setOnCardViewClick();

        }

        public void setOnCardViewClick() {

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     mListener.onRecipeClicked(mRecipes.get(getAdapterPosition()));
                }
            });
        }
    }
}


