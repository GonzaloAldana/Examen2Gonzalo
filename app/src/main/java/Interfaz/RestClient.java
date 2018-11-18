package Interfaz;

import java.util.List;

import Modelo.Category;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface RestClient {

    @GET("getAllCategories")
    Call<List<Category>> cargarCategories();

    @POST("AddCategoryV1")
    Call<Category> insertarCategory(@Body Category cat);

    @PUT("UpdateCategory")
    Call<Category> modificarCategory(@Body Category cat);

    @DELETE("DeleteCategory/{id}")
    Call<String> eliminarCategory( @Path("id") String id);
}
