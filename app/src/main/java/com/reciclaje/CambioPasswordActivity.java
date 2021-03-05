package com.reciclaje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.reciclaje.model.dao.UsuarioDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CambioPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    String idUsuario;
    UsuarioDao usuarioDao;
    Button btnCambiar;
    ImageButton btnHome,btnReciclar,btnPuntos,btnCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_password);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null){
            idUsuario = getIntent().getStringExtra("Id");
        }

        usuarioDao = new UsuarioDao(this);

        btnCambiar = (Button) findViewById(R.id.btnCambiarContraseña);
        btnCambiar.setOnClickListener(this);

        btnHome = (ImageButton) findViewById(R.id.btnHome);
        btnReciclar = (ImageButton) findViewById(R.id.btnReciclaje);
        btnPuntos = (ImageButton) findViewById(R.id.btnPuntos);
        btnCuenta = (ImageButton) findViewById(R.id.btnCuenta);

        btnReciclar.setOnClickListener(this);
        btnPuntos.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCambiarContraseña:
                String password, repetirPassword;
                password = ((EditText) findViewById(R.id.cambiarContraseña)).getText().toString();
                repetirPassword = ((EditText) findViewById(R.id.repiteContraseña)).getText().toString();
                if (password.equals(repetirPassword)){

                    String pass = codePassword(password);
                    int result = usuarioDao.updatePassword(idUsuario, pass);
                    if (result == 1){
                        EditText et_pass = findViewById(R.id.cambiarContraseña);
                        et_pass.setText("");
                        EditText et_repetir = findViewById(R.id.repiteContraseña);
                        et_repetir.setText("");
                        Toast exitoso = Toast.makeText(getApplicationContext(), "Se cambio la contraseña exitosamente", Toast.LENGTH_LONG);
                        exitoso.setGravity(Gravity.RIGHT, 200, 50);
                        exitoso.show();
                    } else {
                        Toast error = Toast.makeText(getApplicationContext(), "Error al cambiar la contraseña", Toast.LENGTH_LONG);
                        error.setGravity(Gravity.RIGHT, 200, 50);
                        error.show();
                    }

                } else {
                    Toast error = Toast.makeText(getApplicationContext(), "La contraseña ingresada no coincide", Toast.LENGTH_LONG);
                    error.setGravity(Gravity.RIGHT, 200, 50);
                    error.show();
                }

                break;

            case R.id.btnHome:
                Intent g = new Intent(CambioPasswordActivity.this, Home.class);
                startActivity(g);
                break;
            case R.id.btnReciclaje:
                Intent p = new Intent(CambioPasswordActivity.this, Reciclaje.class);
                startActivity(p);
                break;
            case R.id.btnPuntos:
                Intent d = new Intent(CambioPasswordActivity.this, Puntos.class);
                startActivity(d);
                break;
            case R.id.btnCuenta:
                Intent c = new Intent(CambioPasswordActivity.this, Account.class);
                startActivity(c);
                break;
        }

    }

    public String codePassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}