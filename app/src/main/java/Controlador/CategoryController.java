package Controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import Adaptador.AdaptadorCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import Modelo.Category;
import Interfaz.RestClient;

public class CategoryController{

    public List<Category> listaCategories;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView recyclerViewArticulos2;

    private ProgressDialog progressDialog;

    private Context context;
    private String baseUrl = "http://apiestudiosalle3.azurewebsites.net/v1/Categories/";

    public void cargarCategories(final RecyclerView recyclerViewArticulos, Context context, String queryBusqueda) {

        context = context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        recyclerViewArticulos2 = recyclerViewArticulos;

        listaCategories = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestClient restClient = retrofit.create(RestClient.class);
        Call<List<Category>> call = restClient.cargarCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                System.out.println(response.code());
                switch (response.code()) {
                    case 200:
                        listaCategories.clear();
                        listaCategories = response.body();
                        mAdapter = new AdaptadorCategory(listaCategories);
                        recyclerViewArticulos.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(call);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }


    public void agregarCategory(Category articulo, Context _context){

        context = _context;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<Category> call = restClient.insertarCategory(articulo);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {

                switch (response.code()) {
                    case 200:
                        Category objArticulo =  new Category();
                        objArticulo =  response.body();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        break;
                    case 401:

                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                System.out.println(call);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }


    public void modificarCategory(Category articulo, Context _context){
        context = _context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(articulo.getCategoryName() + articulo.getCategoryID() + articulo.getDescription());
        progressDialog.setCancelable(true);
        progressDialog.show();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestClient restClient = retrofit.create(RestClient.class);
        Call<Category> call = restClient.modificarCategory(articulo);

        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {

                switch (response.code()) {
                    case 200:
                        Category objArticulo =  new Category();
                        objArticulo =  response.body();
                        Toast.makeText(context,"Se modifico correctamente "+objArticulo.getCategoryName(),Toast.LENGTH_LONG).show();

                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        break;
                    case 401:

                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

                System.out.println(call);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });
    }


    public void eliminarCategory(String id, Context _context){
        context = _context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestClient restClient = retrofit.create(RestClient.class);
        Call<String> call = restClient.eliminarCategory(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                int respuesta  = Integer.parseInt(response.body());
                if (respuesta>0){
                    Toast.makeText(context,"Se elimino correctamente ",Toast.LENGTH_LONG).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }else {
                    Toast.makeText(context,"Error al eliminar ",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(call);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}
