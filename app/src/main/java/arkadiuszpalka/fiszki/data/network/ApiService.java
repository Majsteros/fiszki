package arkadiuszpalka.fiszki.data.network;

import arkadiuszpalka.fiszki.data.network.model.ApiResponse;
import arkadiuszpalka.fiszki.data.network.model.Directories;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Arkadiusz Pałka on 28.04.2018.
 */

public interface ApiService {

    @POST("catalog/add-new")
    @FormUrlEncoded
    Call<String> addDirectory(@Field("name") String name);

    @DELETE("catalog/delete-catalog")
    Call<String> removeDirectory(@Query("catalogID") int id);

    @GET("catalog/get-all")
    Call<Directories> getDirectoriesList();

    @GET("catalog/get-catalogs")
    Call<Directories> getDirectoriesList(@Query("startRow") int startRow, @Query("numberOfRows") int rowsCount);

    @PUT("catalog/rename")
    Call<ApiResponse> renameDirectory(@Query("catalogID") int id, @Query("name") String name);
}
