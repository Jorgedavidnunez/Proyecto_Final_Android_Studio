package com.jd.proyecto_final;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jd.proyecto_final.Models.Productos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>
{
    Context context;
    ArrayList<Productos> productos;
    ArrayList<Productos> lista_original;

    public Adaptador(ArrayList<Productos> productos, MainActivity activity)
    {
        this.productos=productos;
        this.context=activity;
        lista_original=new ArrayList<>();
        lista_original.addAll(productos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Filtrar(final String busqueda)
    {
        int longitud=busqueda.length();
        if(longitud==0)
        {
            productos.clear();
            productos.addAll(lista_original);
        }
        else
        {
            List<Productos> collection = productos.stream().filter(i -> i.getProducto().toLowerCase().contains(busqueda.toLowerCase())).collect(Collectors.toList());
            productos.clear();
            //productos.addAll(collection);
            for(Productos c: lista_original)
            {
                if(c.getProducto().toLowerCase().contains(busqueda.toLowerCase()))
                {
                    productos.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview,parent,false);
        ViewHolder viewHolder=new ViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        final Productos pruebaList = productos.get(position);
        holder.txt_IdProducto.setText(String.valueOf(pruebaList.getIdProducto()));
        holder.txt_producto.setText(String.valueOf(pruebaList.getProducto()));
        holder.txt_marca.setText("Marca: "+pruebaList.getmarca());
        holder.txt_descripcion.setText("Descripcion: "+pruebaList.getDescripcion());
        Glide.with(context).load(pruebaList.getImagen()).into(holder.txt_imagen);
        holder.txt_precio_costo.setText(String.valueOf( "Precio Costo: "+pruebaList.getPrecio_costo()));
        holder.txt_precio_venta.setText(String.valueOf("Precio Venta: "+pruebaList.getPrecio_venta()));
        holder.txt_existencia.setText(String.valueOf("Existencia: "+pruebaList.getExistencia()));
        holder.txt_fecha.setText(pruebaList.getFecha_ingreso());
        boolean isExpandable = productos.get(position).isExpandable();
        holder.expand.setVisibility(isExpandable?View.VISIBLE:View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,String.valueOf(pruebaList.getIdProducto()),Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_IdProducto, txt_producto, txt_marca, txt_descripcion, txt_precio_costo, txt_precio_venta, txt_existencia, txt_fecha;
        ImageView txt_imagen;
        LinearLayout layout_segundo, expand;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txt_IdProducto=itemView.findViewById(R.id.txt_IdProducto);
            txt_producto=itemView.findViewById(R.id.txt_Producto);
            txt_marca=itemView.findViewById(R.id.txt_marca);
            txt_descripcion=itemView.findViewById(R.id.txt_descripcion);
            txt_imagen=itemView.findViewById(R.id.imageview);
            txt_precio_costo=itemView.findViewById(R.id.txt_precio_costo);
            txt_precio_venta=itemView.findViewById(R.id.txt_precio_venta);
            txt_existencia=itemView.findViewById(R.id.txt_existencia);
            txt_fecha=itemView.findViewById(R.id.txt_fecha);
            layout_segundo=itemView.findViewById(R.id.layout_segundo);
            expand=itemView.findViewById(R.id.expand);

            layout_segundo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Productos producto=productos.get(getAdapterPosition());
                    producto.setExpandable(!producto.isExpandable());
                    notifyItemChanged(getAdapterPosition());

                }
            });

        }
    }


}