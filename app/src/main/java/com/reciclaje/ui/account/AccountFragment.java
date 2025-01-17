package com.reciclaje.ui.account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.reciclaje.Editar;
import com.reciclaje.LoginActivity;
import com.reciclaje.Mostrar;
import com.reciclaje.R;
import com.reciclaje.model.dao.UsuarioDao;
import com.reciclaje.model.entity.Usuario;

public class AccountFragment extends AppCompatActivity implements OnClickListener {
    Button btnEditar,btnEliminar,btnMostrar,btnSalir;
    TextView nombre;
    int id=0;
    Usuario u;
    UsuarioDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        nombre = (TextView) findViewById(R.id.nombreUsuario);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        id=b.getInt("Id");
        dao=new UsuarioDao(this);
        u=dao.getUsuarioById(id);
        nombre.setText("Bienvenido "+u.getNombre());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                Intent a=new Intent(AccountFragment.this, Editar.class);
                a.putExtra("Id",id);
                startActivity(a);
                break;
            case R.id.btnEliminar:
                AlertDialog.Builder b= new AlertDialog.Builder(this);
                b.setMessage("¿Estas seguro de eliminar tu cuenta?");
                b.setCancelable(false);
                b.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dao.deleteUsuario(id)) {
                            Toast.makeText(AccountFragment.this, "Registro Eliminado", Toast.LENGTH_LONG).show();
                            Intent a = new Intent(AccountFragment.this, LoginActivity.class);
                            startActivity(a);
                            finish();
                        }else{
                            Toast.makeText(AccountFragment.this, "Error", Toast.LENGTH_LONG).show();

                        }
                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                b.show();
                break;
            case R.id.btnMostrar:
                Intent c=new Intent(AccountFragment.this, Mostrar.class);
                startActivity(c);
                break;
            case R.id.btnSalir:
                Intent i2=new Intent(AccountFragment.this,LoginActivity.class);
                startActivity(i2);
                finish();
                break;
        }
    }
}