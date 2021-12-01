package com.example.senai_pdm_2021_exemplo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    Thread thread;
    private Context context;
    private static final String DATABASE_NAME = "ibook.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE= "book_title";
    private static final String COLUMN_AUTHOR= "book_author";
    private static final String COLUMN_YEAR= "book_editionYear";
    private static final String COLUMN_PAGES= "book_pages";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        thread = null;
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_AUTHOR + " TEXT, " +
                        COLUMN_YEAR + " INTEGER, " +
                        COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addBook(String title, String author, int pages, int year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(
                        "TrabalhoFinal", "Rodando Thread addBook"
                );
                cv.put(COLUMN_TITLE, title);
                cv.put(COLUMN_AUTHOR, author);
                cv.put(COLUMN_YEAR, year);
                cv.put(COLUMN_PAGES, pages);
                long result = db.insert(TABLE_NAME,null, cv);
                if(result == -1){
                    Toast.makeText(context, "Cadastro não registrado!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Cadastro registrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateDate(String row_id, String title, String author, String year, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == 1){
            Toast.makeText(context, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Livro não alterado!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Livro não removido!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deletado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

}
