package com.example.abd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    private EditText documento, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        documento = findViewById(R.id.txtDocumento);
        nombre= findViewById(R.id.txtNombre);

    }


    //Metodo para registrar los producto, usuario o dar de alta los productos

    public void Registrar(View view){

        AdminSQLite admin = new AdminSQLite(this, "Administrar", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        if (documento.getText().toString().equals("") || nombre.getText().toString().equals("")){
            Toast.makeText(this,"Documento o nombre vacio", Toast.LENGTH_LONG).show();


        }else {
            int doc = Integer.parseInt(documento.getText().toString());
            String nom = nombre.getText().toString();

            ContentValues datos = new ContentValues();

            datos.put("documento", doc);
            datos.put("nombre", nom);

            BaseDeDatos.insert("usuario", null, datos);
            BaseDeDatos.close();

            nombre.setText("");
            documento.setText("");

            Toast.makeText(this, "Usuario registrado con exito", Toast.LENGTH_LONG).show();
        }


    }

    // Metodo de consulta

    public void Consultar (View view){

        AdminSQLite admin = new AdminSQLite(this, "Administrar", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int doc = Integer.parseInt(documento.getText().toString());

        Cursor fila = BaseDeDatos.rawQuery("select * from usuario where documento="+doc, null);

        if (fila.moveToFirst()){
            nombre.setText(fila.getString(1));
        }else {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG).show();
        }
        BaseDeDatos.close();
    }

    public void Eliminar (View view){

        AdminSQLite admin = new AdminSQLite(this, "Administrar", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int doc = Integer.parseInt(documento.getText().toString());

        int val = BaseDeDatos.delete("usuario", "documento="+doc, null);
        BaseDeDatos.close();

        documento.setText("");
        nombre.setText("");

        if (val==1){

            Toast.makeText(this,"Usuario Eliminado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG).show();
        }
    }

    public void Actualizar(View view){

        AdminSQLite admin = new AdminSQLite(this, "Administrar", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int doc = Integer.parseInt(documento.getText().toString());
        String nom = nombre.getText().toString();

        ContentValues datos = new ContentValues();
        datos.put("documento", doc);
        datos.put("nombre", nom);

        int val = BaseDeDatos.update("usuario", datos,"documento="+doc, null);
        BaseDeDatos.close();

        documento.setText("");
        nombre.setText("");

        if (val == 1){
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Error actualizando usuario", Toast.LENGTH_LONG).show();
        }
    }

}