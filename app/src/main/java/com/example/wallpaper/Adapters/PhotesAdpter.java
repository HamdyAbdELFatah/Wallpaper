package com.example.wallpaper.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wallpaper.Activites.FullScreenPhotoActivity;
import com.example.wallpaper.Models.Photo;
import com.example.wallpaper.R;
import com.example.wallpaper.Utils.SquareImageView;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
public class PhotesAdpter extends RecyclerView.Adapter<PhotesAdpter.ViewHolder> {
    public PhotesAdpter(Context context, List<Photo> photes) {
        this.context = context;
        this.photes = photes;
    }
    Context context;
    List<Photo> photes;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteam_photo,viewGroup,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Photo photo=photes.get(i);
        holder.username.setText(photo.getUser().getUsername());
        GlideApp.with(context)
        .load(photo.getUrl().getRegular())
        .placeholder(R.drawable.notfound)
        .override(600,600)
        .into(holder.photo_photo);
        GlideApp.with(context)
        .load(photo.getUser().getProfileImage().getSmall())
        .into(holder.avatar);
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String photoId=photes.get(i).getId();
                Intent intent=new Intent(context, FullScreenPhotoActivity.class);
                intent.putExtra("photoid",photoId);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return photes.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView avatar;
        TextView username;
        SquareImageView photo_photo;
        FrameLayout frameLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.iteam_photo_user_avatar);
            username=itemView.findViewById(R.id.iteam_photo_username);
            photo_photo=itemView.findViewById(R.id.iteam_photo_photo);
            frameLayout=itemView.findViewById(R.id.iteam_frame);

        }
    }
}
