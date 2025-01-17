package com.reciclaje.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.reciclaje.model.entity.Persona;
import com.reciclaje.model.entity.Usuario;

import java.util.ArrayList;

public class UsuarioDao {
    SQLiteDatabase sql;
    ArrayList<Usuario> lista = new ArrayList<Usuario>();
    Usuario u;
    Context c;
    String bd = "BDUsuarios";
    String tabla = "create table if not exists usuario(id integer primary key autoincrement, usuario text, pass text, nombre text,ciudad text)";


    public UsuarioDao(Context c) {
        this.c = c;
        sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u = new Usuario();
    }

    public Long insertUsuario(Usuario u) {
        if (buscar(u.getUsuario()) == 0) {
            ContentValues cv = new ContentValues();
            cv.put("usuario", u.getUsuario());
            cv.put("pass", u.getPassword());
            cv.put("nombre", u.getNombre());
            cv.put("ciudad", u.getCiudad());
            return (sql.insert("usuario", null, cv));
        } else {
            return null;
        }
    }

    public int buscar(String u) {
        int x = 0;
        lista = selectUsuarios();
        for (Usuario us : lista) {
            if (us.getUsuario().equals(u)) {
                x++;
            }
        }
        return x;
    }


    public ArrayList<Usuario> selectUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        lista.clear();
        Cursor cr = sql.rawQuery("select*from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                Usuario u = new Usuario();
                u.setId(cr.getInt(0));
                u.setUsuario(cr.getString(1));
                u.setPassword(cr.getString(2));
                u.setNombre(cr.getString(3));
                u.setCiudad(cr.getString(4));
                lista.add(u);
            } while (cr.moveToNext());
        }
        return lista;
    }

    public int login(String u, String p) {
        int a = 0;
        Cursor cr = sql.rawQuery("select*from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(1).equals(u) && cr.getString(2).equals(p)) {
                    a++;
                }
            } while (cr.moveToNext());
        }
        return a;

    }

    public Usuario getUsuario(String u, String p){
        lista=selectUsuarios();
        for(Usuario us:lista){
            if(us.getUsuario().equals(u)&&us.getPassword().equals(p)){
                return us;
            }
        }
        return  null;
    }

    public  Usuario getUsuarioById(int id) {
        lista = selectUsuarios();
        for (Usuario us : lista) {
            if (us.getId() == id) {
                return us;
            }
        }
        return null;
    }

    public boolean updateUsuario(Usuario u) {
        ContentValues cv = new ContentValues();
        cv.put("usuario", u.getUsuario());
        cv.put("pass", u.getPassword());
        cv.put("nombre", u.getNombre());
        cv.put("ciudad", u.getCiudad());
/*
    OJO AL CAMNBIO EN EL TERCER Y CUARTO PARÀMETROS
    el cuarto paràmetro debe ser convertido primero a String
     */
        //return (sql.update("usuario", cv,"id"+u.getId(),null)> 0);
        return (sql.update("usuario", cv,"id=?",new String[]{String.valueOf(u.getId())})> 0);

    }

    /*
    ESTE METODDO UTILIZA UN Integer PARA PODER HACER UN .toString(). ADICIONALMENTE DEBES
    ENVIAR EL ARGUMENTO EN EL TERCER PARÀMETRO. EN LA CLAUSULA WHERE EL SIGNO DE INTERROGACIÒN DE id=?
    SERÀ REEMPLAZADO POR EL PRIMER VALOR QUE SE ENCUENTRA EN EL TERCER PARÀMETRO DENTRO DEL VECTOR DE
    STRINGS
     */
    public boolean deleteUsuario(Integer id){
        return (sql.delete("usuario","id=?",new String[]{id.toString()})>0);
    }

    public int updatePassword(String idUsuario, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pass", password);
        return (sql.update("usuario", contentValues,"id=?",new String[] {idUsuario}));
    }

}

