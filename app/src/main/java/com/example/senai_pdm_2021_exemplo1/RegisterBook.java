package com.example.senai_pdm_2021_exemplo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// SOLID - Princípio da Responsabilidade Única (S)
// Uma classe tem que ser responsável por uma única tarefa (SALVAR O LIVRO)
public class RegisterBook extends AppCompatActivity {

    EditText title_input, author_input,editionYear_input,  pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_book);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        editionYear_input = findViewById(R.id.editionYear_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database myDb = new Database(RegisterBook.this);
                myDb.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(editionYear_input.getText().toString().trim()),
                        Integer.valueOf(pages_input.getText().toString().trim()));
            }
        });
    }
}