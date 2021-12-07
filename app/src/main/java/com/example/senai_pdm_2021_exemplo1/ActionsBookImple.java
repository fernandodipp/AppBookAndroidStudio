package com.example.senai_pdm_2021_exemplo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// SOLID - Princípio da Segregação da Interface (PSI)
// Definir uma interface enxuta, mais específica, onde suas classes filhas,
// vão fazer uso de todos os métodos
public class ActionsBookImple extends AppCompatActivity implements ActionsBook {

    EditText title_input, author_input, editionYear_input, pages_input;
    Button btnEdit, btnDelete;

    String id, title, author, year, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_book);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        editionYear_input = findViewById(R.id.editionYear_input2);
        pages_input = findViewById(R.id.pages_input2);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(ActionsBookImple.this);
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                year = editionYear_input.getText().toString().trim();
                pages= pages_input.getText().toString().trim();

                // Padrão de Projeto Estrutural - FACADE
                if(validarDados(title, author, year, pages)) {
                    db.updateDate(id, title, author, year, pages);
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        getAndSetIntentData();
    }

    public void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") &&
                getIntent().hasExtra("year") && getIntent().hasExtra("pages")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            year = getIntent().getStringExtra("year");
            pages = getIntent().getStringExtra("pages");


            title_input.setText(title);
            author_input.setText(author);
            editionYear_input.setText(year);
            pages_input.setText(pages);
            Log.d("stev", title + " " + author + " " + year + " " + pages);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Você tem certeza que deseja deletar " + title + " ?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                Database db = new Database(ActionsBookImple.this);
                db.deleteOneRow(id);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    protected boolean validarDados(String title, String author, String year, String pages){

        if(!validarTitulo(title)){
            return false;
        }
        if(!validarAutor(author)){
            return false;
        }
        if(!validarAno(year)){
            return false;
        }
        if(!validarPaginas(pages)){
            return false;
        }

        return true;
    }

    protected  boolean validarTitulo(String title) {
        if(title.equals("")){
            Toast.makeText(this, "Favor inserir um título", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    protected  boolean validarAutor(String author) {
        if(author.equals("")){
            Toast.makeText(this, "Favor inserir um autor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    protected  boolean validarAno(String year) {
        if(year.equals("")){
            Toast.makeText(this, "Favor inserir um ano", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    protected  boolean validarPaginas(String pages) {
        if(pages.equals("")){
            Toast.makeText(this, "Favor inserir um número de páginas", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}