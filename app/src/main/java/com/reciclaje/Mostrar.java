package com.reciclaje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.reciclaje.model.dao.UsuarioDao;
import com.reciclaje.model.entity.Usuario;

import java.util.ArrayList;

public class Mostrar extends AppCompatActivity {
    ListView lista;
    UsuarioDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);
        lista=(ListView)findViewById(R.id.lista);

        dao=new UsuarioDao(this);
        ArrayList<Usuario> l=dao.selectUsuarios();

        ArrayList<String>list=new ArrayList<String>();
        for (Usuario u:l){
            list.add(u.getNombre()+" - "+u.getCiudad());
        }
        ArrayAdapter<String>a=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,list);
        lista.setAdapter(a);
    }
}