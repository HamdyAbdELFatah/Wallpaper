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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wallpaper.Adapters.GlideApp;
import com.example.wallpaper.Adapters.PhotesAdpter;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Constant;
import com.example.wallpaper.Webservices.ApiInterface;
import com.example.wallpaper.Webservices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionFragment extends Fragment {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    PhotesAdpter photesAdpter;
    List<Photo> photos=new ArrayList<>();
    TextView title;
    TextView description;
    TextView username;
    CircleImageView circleImg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_collection,container,false);
         progressBar=v.findViewById(R.id.fragment_collection_progressbar);
         recyclerView=v.findViewById(R.id.fragment_collection_recyclerview);
         title=v.findViewById(R.id.fragment_collection_title);
        description=v.findViewById(R.id.fragment_collection_description);
        username=v.findViewById(R.id.fragment_collection_username);
        circleImg=v.findViewById(R.id.fragment_collection_user_avater);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photesAdpter=new PhotesAdpter(getActivity(),photos);
        recyclerView.setAdapter(photesAdpter);
        showProgressBar(true);
        Bundle bundle=getArguments();
        int collectionId=bundle.getInt("CollectionId");
        getInformatinOfCollection(collectionId);
        getPhotoOfCollection(collectionId);
        return v;
    }
    private void getInformatinOfCollection(int id){
        ApiInterface apiInterface= ServiceGenerator.createService(ApiInterface.class);
        Call<Collection> call=apiInterface.getInformationOfCollections(id,Constant.APPLICATION_ID);
        call.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if(response.isSuccessful()){
                    Collection collection=response.body();
                    title.setText(collection.getTitle());
                    description.setText(collection.getDescription());
                    username.setText(collection.getUser().getUsername());
                    GlideApp
                            .with(getActivity())
                            .load(collection.getUser().getProfileImage().getSmall())
                            .into(circleImg);
                }else{
                    Toast.makeText(getActivity(), "Failed "+response.message(), Toast.LENGTH_SHORT).show();
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }    private void getPhotoOfCollection(int id){
        ApiInterface apiInterface= ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> call=apiInterface.getPhotoOfCollections(id,Constant.APPLICATION_ID);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if(response.isSuccessful()){
                    photos.addAll(response.body());
                    photesAdpter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity(), "Failed "+response.message(), Toast.LENGTH_SHORT).show();
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                showProgressBar(false);
            }
        });
    }


    private void showProgressBar(boolean isShow){
        if(isShow){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
