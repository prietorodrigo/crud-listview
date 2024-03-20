package com.example.crud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BebidaDAO {
    private Banco banco;
    public BebidaDAO(Banco b) {
        this.banco = b;
    }
    public long save(Bebida bebida) {
        long id = bebida.getId();
        SQLiteDatabase db = banco.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nome", bebida.getNome());
            values.put("preco", bebida.getPreco());
            values.put("volume", bebida.getVolume());
            values.put("sabor", bebida.getSabor());
            if (id==0) {
                //novo registro
                id = db.insert("bebidas", "", values);
            }
            else {
                //atualizar registro
                String _id = String.valueOf(bebida.getId());
                String[] whereArgs = new String[]{_id};
                id = db.update("bebidas", values, "id=?", whereArgs);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            //db.close();
        }
        return id;
    }

    public long update(Bebida bebida) {
        long id = bebida.getId();
        SQLiteDatabase db = banco.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("id", bebida.getId());
            values.put("nome", bebida.getNome());
            values.put("preco", bebida.getPreco());
            values.put("volume", bebida.getVolume());
            values.put("sabor", bebida.getSabor());
            System.out.println(bebida.getId());
            //atualizar registro
            String _id = String.valueOf(bebida.getId());
            String[] whereArgs = new String[]{_id};
            id = db.update("bebidas", values, "id=?", new String[]{bebida.getId()+""});

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            //db.close();
        }
        return id;
    }

    public long delete(Bebida bebida) {
        long id = bebida.getId();
        SQLiteDatabase db = banco.getWritableDatabase();
        try{
            String _id = String.valueOf(bebida.getId());
            String[] whereArgs = new String[]{_id};
            id = db.delete("bebidas","id=?", new String[]{bebida.getId()+""});

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            //db.close();
        }
        return id;
    }

    @SuppressLint("Range")
    private List<Bebida> paraLista(Cursor c) {
        List<Bebida> bebidas = new ArrayList<>();
        if (c.moveToFirst()) {
            do{
                Bebida b = new Bebida();
                b.setId(c.getInt(c.getColumnIndex("id")));
                b.setNome(c.getString(c.getColumnIndex("nome")));
                b.setPreco(c.getInt(c.getColumnIndex("preco")));
                b.setVolume(c.getString(c.getColumnIndex("volume")));
                b.setSabor(c.getString(c.getColumnIndex("sabor")));
                bebidas.add(b);

            } while (c.moveToNext());
        }
        return bebidas;
    }
    public List<Bebida> buscarTodos(){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor c = null;
        try {
            //c = db.query("aluno", null, null, null, null, null, null);
            c = db.rawQuery("SELECT * FROM bebidas", null);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            //db.close();
        }
        if (c != null) {
            return paraLista(c);
        }
        return null;
    }
}
