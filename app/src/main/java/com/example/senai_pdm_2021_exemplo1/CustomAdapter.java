package com.example.senai_pdm_2021_exemplo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Padrão de Projeto Estrutural - ADAPTER
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context contex;
    Activity activity;
    private ArrayList book_id, book_title, book_author, book_editionYear, book_pages;

    CustomAdapter(Activity activity, Context contex, ArrayList book_id, ArrayList book_title, ArrayList book_author,ArrayList book_editionYear, ArrayList book_pages){
        this.activity = activity;
        this.contex = contex;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_editionYear = book_editionYear;
        this.book_pages = book_pages;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contex);
        View view = inflater.inflate(R.layout.list_book, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_year_txt.setText(String.valueOf(book_editionYear.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));
        holder.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contex, ActionsBookImple.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("year", String.valueOf(book_editionYear.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView book_title_txt, book_author_txt, book_year_txt, book_pages_txt;
        private Button btnRead;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_year_txt = itemView.findViewById(R.id.book_year_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            btnRead = itemView.findViewById(R.id.btnRead);
        }
    }
}
