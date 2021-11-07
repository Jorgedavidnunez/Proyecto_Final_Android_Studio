package com.jd.proyecto_final;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.jd.proyecto_final.Interfaz.Post_Productos;
import com.jd.proyecto_final.Models.Productos;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView search;
    LinearLayout segundo_layout;
    TextView txt_IdProducto, txt_producto, txt_marca, txt_descripcion, txt_precio_costo, txt_precio_venta, txt_existencia, txt_fecha;
    ImageView txt_imagen;
    RecyclerView recycle_view;
    ArrayList<Productos> arraylist;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.search);
        segundo_layout=findViewById(R.id.layout_segundo);
        txt_IdProducto=findViewById(R.id.txt_IdProducto);
        txt_producto=findViewById(R.id.txt_Producto);
        txt_marca=findViewById(R.id.txt_marca);
        txt_descripcion=findViewById(R.id.txt_descripcion);
        txt_imagen=findViewById(R.id.imageview);
        txt_precio_costo=findViewById(R.id.txt_precio_costo);
        txt_precio_venta=findViewById(R.id.txt_precio_venta);
        txt_existencia=findViewById(R.id.txt_existencia);
        txt_fecha=findViewById(R.id.txt_fecha);


        recycle_view=findViewById(R.id.recycle_view);
        arraylist = new ArrayList<>();

        recycle_view=findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));

        Obtener_Datos();

        search.setOnQueryTextListener(this);

    }

    private void Obtener_Datos()
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:5000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        Post_Productos post_productos = retrofit.create(Post_Productos.class);
        Call<List<Productos>> call = post_productos.getDatos();
        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if(!response.isSuccessful())
                {
                    //Idtexto.setText("codigo: "+response.code());
                    return;
                }
                List<Productos> producto = response.body();
                for(Productos prod:producto)
                {
                    String url="http://10.0.2.2:8080"+prod.getImagen();
                    arraylist.add(new Productos(prod.getIdProducto(), prod.getProducto(), prod.getmarca(), prod.descripcion, url, prod.getPrecio_costo(), prod.getPrecio_venta(), prod.getExistencia(), prod.getFecha_ingreso()));
                }
                adaptador = new Adaptador(arraylist, MainActivity.this);
                recycle_view.setAdapter(adaptador);

            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                //Idtexto.setText(t.getMessage());
            }
        });
    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void expand(View view) {
        /*int v=(txt_descripcion.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(segundo_layout, new AutoTransition());
        txt_descripcion.setVisibility(v);*/

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onQueryTextChange(String s) {
        adaptador.Filtrar(s);
        return false;
    }
}