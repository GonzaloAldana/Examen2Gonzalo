package Adaptador;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gonzalo.retrofitgonzalo.R;

import java.util.List;

import Modelo.Category;

public class AdaptadorCategory extends  RecyclerView.Adapter<AdaptadorCategory.ViewHolder> {

    private List<Category> categoryModel;

    public AdaptadorCategory(List<Category> categoryModel) {
        this.categoryModel = categoryModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String nombre = categoryModel.get(position).getCategoryName();
        String descripcion =  categoryModel.get(position).getDescription();

        holder.titulo.setText(nombre);
        holder.descripcion.setText(descripcion);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo,descripcion;

        public ViewHolder(View v) {
            super(v);
            titulo = (TextView) v.findViewById(R.id.lblTitulo);
            descripcion = (TextView) v.findViewById(R.id.lblDescripcion);
        }
    }
}