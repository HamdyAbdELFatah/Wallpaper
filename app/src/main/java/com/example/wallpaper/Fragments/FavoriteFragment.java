package com.example.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wallpaper.Adapters.PhotesAdpter;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment  extends Fragment {
    RecyclerView recyclerView;
    TextView textView;
    PhotesAdpter photesAdpter;
    List<Photo> photo=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_favorite,container,false);
        recyclerView=v.findViewById(R.id.fragment_Favorite_recyclerView);
        textView=v.findViewById(R.id.fragment_Favorite_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photesAdpter=new PhotesAdpter(getActivity(),photo);
        recyclerView.setAdapter(photesAdpter );
        getPhoto();
        return v;
    }

    private void getPhoto() {
        RealmController realmController=new RealmController();
        photo.addAll(realmController.getPhotos());
        if(photo.size()==0){
            textView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            photesAdpter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
