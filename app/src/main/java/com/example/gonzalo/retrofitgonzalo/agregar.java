package com.example.gonzalo.retrofitgonzalo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;

import Controlador.CategoryController;
import Modelo.Category;


public class agregar extends Fragment {
    public agregar() {
        // Required empty public constructor
    }
    boolean isModified = false;
    EditText txtNombre, txtDescripcion;
    int idEdit;
    Category objCategoryEdit;
    String titulo ="Agregar Nuevo Elemento";

    @SuppressLint("ValidFragment")
    public agregar(boolean m, String category) {
        this.isModified = m;
        this.objCategoryEdit = new Category();
        Gson objGson = new Gson();
        this.objCategoryEdit =  objGson.fromJson(category,Category.class);
        this.idEdit = objCategoryEdit.getCategoryID();
        this.titulo = "Modificar Elemento";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_agregar, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(titulo);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Arriba Python");
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtNombre = view.findViewById(R.id.txtNombre);

        if (isModified)
        {
            txtNombre.setText(objCategoryEdit.getCategoryName());
            txtDescripcion.setText(objCategoryEdit.getDescription());
            idEdit = objCategoryEdit.getCategoryID();
        }

        return  view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnuGuardar){
            CategoryController articulosController =  new CategoryController();

            Category articulo =  new Category();
            articulo.setCategoryName(txtNombre.getText().toString());
            articulo.setDescription(txtDescripcion.getText().toString());

            if(isModified){
                objCategoryEdit.setDescription(txtDescripcion.getText().toString());
                objCategoryEdit.setCategoryName(txtNombre.getText().toString());
                articulosController.modificarCategory(objCategoryEdit,getActivity());
            }else {
                articulosController.agregarCategory(articulo, getActivity());
            }

            Fragment fragment = new ConsultaApi();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_agregar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
