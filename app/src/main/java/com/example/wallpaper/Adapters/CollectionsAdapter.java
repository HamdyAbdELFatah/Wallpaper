package com.example.wallpaper.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wallpaper.Fragments.CollectionFragment;
import com.example.wallpaper.Fragments.CollectionsFragmen;
import com.example.wallpaper.Fragments.FavoriteFragment;
import com.example.wallpaper.Fragments.PhotesFragment;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Functions;
import com.example.wallpaper.Utils.SquareImageView;

import java.util.List;

public class CollectionsAdapter extends BaseAdapter {
    public CollectionsAdapter(Context context, List<Collection> collections) {
        this.context = context;
        this.collections = collections;
    }
    Context context;
    List<Collection> collections;
    @Override
    public int getCount() {
        return collections.size();
    }
    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_collections,parent,false);
        SquareImageView collectionphoto=v.findViewById(R.id.item_collection_photo);
        TextView title=v.findViewById(R.id.item_collection_title);
        TextView totalphotos=v.findViewById(R.id.item_collection_total_photos);
        FrameLayout frameLayout=v.findViewById(R.id.item_collections_frame);
        title.setText(collections.get(i).getTitle());
        totalphotos.setText(collections.get(i).getTotalPhotos()+" photos");
        GlideApp
                .with(context)
                .load(collections.get(i).getCoverPhoto().getUrl().getRegular())
                .into(collectionphoto);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("CollectionId",collections.get(i).getId());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                CollectionFragment collectionFragment =new CollectionFragment();
                collectionFragment .setArguments(bundle);
                Functions.changeMainFragmentWithBack(activity,collectionFragment);
            }
        });
        return v;
    }
}
