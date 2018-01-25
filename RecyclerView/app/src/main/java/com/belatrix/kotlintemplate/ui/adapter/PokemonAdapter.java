package com.belatrix.kotlintemplate.ui.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrix.kotlintemplate.R;
import com.belatrix.kotlintemplate.model.Pokemon;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by emedinaa on 14/08/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> pokemonList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tviName;
        public ImageView iviPhoto;
        public View view;
        public ViewHolder(View  v) {
            super(v);
            this.view = v;
            tviName= (TextView) v.findViewById(R.id.tviName);
            iviPhoto= (ImageView) v.findViewById(R.id.iviPhoto);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PokemonAdapter(Context context,List<Pokemon> pokemonList) {
        this.context= context;
        this.pokemonList = pokemonList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pokemon, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pokemon pokemon= pokemonList.get(position);
        holder.tviName.setText(pokemon.getName());
        holder.iviPhoto.setImageBitmap(getBitmapFromAssets(pokemon.getPhoto()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    Log.v("ADAPTER", "iviPhoto "+holder.iviPhoto);
                    ViewCompat.setTransitionName(holder.iviPhoto, "iviPhoto");
                    onItemClickListener.onClikListener(position,view,holder.iviPhoto);
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }
}
