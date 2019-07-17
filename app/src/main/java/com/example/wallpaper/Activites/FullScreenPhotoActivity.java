package com.example.wallpaper.Activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.Constant;
import com.example.wallpaper.Utils.Functions;
import com.example.wallpaper.Utils.RealmController;
import com.example.wallpaper.Webservices.ApiInterface;
import com.example.wallpaper.Webservices.ServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class FullScreenPhotoActivity  extends AppCompatActivity implements View.OnClickListener {
    ImageView fullScreenphoto;
    CircleImageView userAvater;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabFavorite;
    FloatingActionButton fabWallpaper;
    TextView username;
    Bitmap photoBitmap;
    RealmController realmController;
    Photo photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        fullScreenphoto=findViewById(R.id.activity_fullscreen_photo_photo);
        userAvater=findViewById(R.id.activity_fullscreen_photo_user_avatar);
        username=findViewById(R.id.activity_fullscreen_photo_username);
        floatingActionMenu=findViewById(R.id.activity_fullscreen_photo_fab_menu);
        fabFavorite=findViewById(R.id.activity_fullscreen_photo_fab_favorite);
        fabFavorite.setOnClickListener(this);
        fabWallpaper=findViewById(R.id.activity_fullscreen_photo_fab_wallpaper);
        fabWallpaper.setOnClickListener(this);
        String id=getIntent().getStringExtra("photoid");
        getPhoto(id);
        realmController=new RealmController();
        if(realmController.isPhotoExist(id)){
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.red_favorite));
        }
    }
    private void getPhoto(String id) {
        ApiInterface apiInterface= ServiceGenerator.createService(ApiInterface.class);
        Call<Photo> call=apiInterface.getPhoto(id, Constant.APPLICATION_ID);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if(response.isSuccessful()){
                     photo=response.body();
                    updateUI(photo);
                }else{

                }
            }
            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }
    private void updateUI(Photo photo) {
        try{
            username.setText(photo.getUser().getUsername());
            Glide.with(FullScreenPhotoActivity.this)
                    .load(photo.getUser().getProfileImage().getSmall()).into(userAvater);
            Glide.with(FullScreenPhotoActivity.this).asBitmap()
                    .load(photo.getUrl().getRegular()).centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                          fullScreenphoto.setImageBitmap(resource);
                          photoBitmap=resource;
                        }
                    });

        }catch (Exception e){

        }
    }
    @Override
    public void onClick(View v) {
        floatingActionMenu.close(true);
        if(v.getId()==R.id.activity_fullscreen_photo_fab_wallpaper){
            if(photoBitmap!=null){
                if(Functions.setWallpaper(FullScreenPhotoActivity.this,photoBitmap)){
                    Toast.makeText(FullScreenPhotoActivity.this, "Set Wallpaper Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FullScreenPhotoActivity.this, "Set Wallpaper Fail", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            if(realmController.isPhotoExist(photo.getId())){
                realmController.deletePhoto(photo);
                fabFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.favorite));
            }
            else{
                realmController.savePhoto(photo);
                fabFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.red_favorite));
            }
        }
    }
}
