package com.example.senai_pdm_2021_exemplo1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ReadBook extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    TextView permission;

    String[] appPermission = {
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int CODIGO_PERMISSOES_REQUERIDAS = 1;

    Database myDb;
    ArrayList<String> book_id, book_title, book_author, book_editionYear, book_pages;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        permission = findViewById(R.id.permission);

        verificarPermissao();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadBook.this, RegisterBook.class);
                startActivity(intent);
            }
        });

        myDb = new Database(ReadBook.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_editionYear = new ArrayList<>();
        book_pages = new ArrayList<>();


        storeDataInArrays();
        customAdapter = new CustomAdapter(ReadBook.this, this, book_id, book_title, book_author, book_editionYear, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReadBook.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    public boolean verificarPermissao(){
        List<String> permissoesRequeridas = new ArrayList<>();

        for(String permission : appPermission){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissoesRequeridas.add(permission);

            }
        }

        if(!permissoesRequeridas.isEmpty()){
            ActivityCompat.requestPermissions(this, permissoesRequeridas.toArray(new String[permissoesRequeridas.size()]),
                    CODIGO_PERMISSOES_REQUERIDAS);
            return false;
        }

        return true;
    }

    void storeDataInArrays(){
        Cursor cursor = myDb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_editionYear.add(cursor.getString(4));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}
