package com.example.wallpaper.Webservices;
import com.example.wallpaper.Adapters.CollectionsAdapter;
import com.example.wallpaper.Models.Collection;
import com.example.wallpaper.Models.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiInterface {
    @GET("photos/?")
    Call<List<Photo>> getPhotos(@Query("client_id") String id);
    @GET("collections/featured/?")
    Call<List<Collection>> getCollections(@Query("client_id") String id);
    @GET("collections/{id}/?")
    Call<Collection> getInformationOfCollections(@Path("id")int id,@Query("client_id") String client_id);
    @GET("collections/{id}/photos/?")
    Call<List<Photo>> getPhotoOfCollections(@Path("id")int id,@Query("client_id") String client_id);
    @GET("photos/{id}/?")
    Call<Photo> getPhoto(@Path("id")String id,@Query("client_id") String client_id);
}
