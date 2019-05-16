package com.example.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etC, etD, etP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etC = (EditText)findViewById(R.id.txt_codigo);
        etD = (EditText)findViewById(R.id.txt_descripcion);
        etP = (EditText)findViewById(R.id.txt_precio);
    }

    //Metodo para almacenar los productos
    public void Registrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etC.getText().toString();
        String desc = etD.getText().toString();
        String precio = etP.getText().toString();

        if(!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", desc);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();
            etC.setText("");
            etD.setText("");
            etP.setText("");

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes llenar todos los cmapos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Buscar un articulo
    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etC.getText().toString();
        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo= "+codigo, null);
            if(fila.moveToFirst()){
                etD.setText(fila.getString(0));
                etP.setText(fila.getString(1));
                BaseDeDatos.close();
            }else{
                Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo Eliminar un producto
    public void Eliminar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etC.getText().toString();
        if(!codigo.isEmpty()){
            int cantidad = BaseDeDatos.delete("articulos", "codigo= "+codigo, null);
            BaseDeDatos.close();

            etC.setText("");
            etD.setText("");
            etP.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Artículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo Modificar valores
    public void Modificar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etC.getText().toString();
        String descripcion = etD.getText().toString();
        String precio = etP.getText().toString();

        if(!codigo.isEmpty() && !precio.isEmpty() && !descripcion.isEmpty()){
            ContentValues Registro = new ContentValues();
            Registro.put("codigo", codigo);
            Registro.put("descripcion", descripcion);
            Registro.put("precio", precio);

            int cantidad = BaseDeDatos.update("articulos", Registro,"codigo= "+codigo, null);
            BaseDeDatos.close();

            if(cantidad == 1){
                Toast.makeText(this, "Artículo modificado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


}
