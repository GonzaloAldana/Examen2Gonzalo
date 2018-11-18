package com.example.gonzalo.retrofitgonzalo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.google.gson.Gson;

import java.util.List;

import Controlador.CategoryController;
import Modelo.Category;
import Utilidades.SwipeHelper;


public class ConsultaApi extends Fragment{


        public ConsultaApi() {
            // Required empty public constructor
        }
    RecyclerView recyclerViewCategory;
    FloatingActionButton fbButton;
    SwipeRefreshLayout swipeRefreshLayout;
    CategoryController controladorCategories;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View view =  inflater.inflate(R.layout.fragment_fragment1, container, false);

            recyclerViewCategory = view.findViewById(R.id.listaArticulos);
            fbButton  = view.findViewById(R.id.fbAgregar);
            swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

            ObtenerDatos();
            EventoSwipe();
            SwipeRefresh();
            EventoBoton();

            return  view;
        }
    public void ObtenerDatos(){


        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        recyclerViewCategory.setLayoutManager(llm);
        recyclerViewCategory.setHasFixedSize(true);

        controladorCategories =  new CategoryController();
        controladorCategories.cargarCategories(recyclerViewCategory, getActivity(), null);
    }

    public void EventoBoton(){
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new agregar();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment ).commit();
            }
        });
    }
    public void SwipeRefresh(){

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        ObtenerDatos();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                });
    }

    public void EventoSwipe(){

        SwipeHelper swipeHelper = new SwipeHelper(getActivity(), recyclerViewCategory) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Eliminar",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButton.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete

                                Category objCategory =  new Category();
                                objCategory = controladorCategories.listaCategories.get(pos);
                                Gson objGson = new Gson();

                                controladorCategories.eliminarCategory(String.valueOf(objCategory.getCategoryID()),getActivity());
                                controladorCategories.listaCategories.remove(pos);
                                controladorCategories.mAdapter.notifyDataSetChanged();
                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Editar",
                        0,
                        Color.parseColor("#3232ff"),
                        new SwipeHelper.UnderlayButton.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnUnshare

                                Intent i = new Intent(getActivity(),agregar.class);
                                Category objCat =  new Category();
                                objCat = controladorCategories.listaCategories.get(pos);
                                Gson objGson = new Gson();

                                String artString  = objGson.toJson(objCat);

                                Fragment fragment = new agregar(true,artString);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, fragment).commit();
                            }
                        }
                ));
            }
        };
    }
}