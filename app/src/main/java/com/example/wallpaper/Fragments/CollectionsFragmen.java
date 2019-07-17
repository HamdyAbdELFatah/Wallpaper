package com.example.wallpaper.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wallpaper.Adapters.CollectionsAdapter;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Constant;
import com.example.wallpaper.Webservices.ApiInterface;
import com.example.wallpaper.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsFragmen  extends Fragment {
    ProgressBar progressBar;
    GridView gridView;
    CollectionsAdapter collectionsAdapter;
    List<Collection> collections=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_collections,container,false);
        progressBar=v.findViewById(R.id.fragment_collections_progressbar);
        gridView=v.findViewById(R.id.fragment_collections_grideview);
        collectionsAdapter=new CollectionsAdapter(getActivity(),collections);
        gridView.setAdapter(collectionsAdapter);
        showProgressBar(true);
        getCollections();
        return v;
    }

    public FragmentActivity getMyContext() {
        return getActivity();
    }
    private void getCollections() {
        ApiInterface apiInterface= ServiceGenerator.createService(ApiInterface.class);
        Call<List<Collection>> call=apiInterface.getCollections(Constant.APPLICATION_ID);
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.isSuccessful()){
                    collections.addAll(response.body());
                    collectionsAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "failed "+response.message(), Toast.LENGTH_SHORT).show();
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Toast.makeText(getActivity(), "failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                showProgressBar(false);

            }
        });
    }
    private void showProgressBar(boolean isShow){
        if(isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

