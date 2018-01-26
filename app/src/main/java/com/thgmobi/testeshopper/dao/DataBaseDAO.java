package com.thgmobi.testeshopper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thgmobi.testeshopper.model.Produto;
import com.thgmobi.testeshopper.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 24/01/2018.
 */

public class DataBaseDAO extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Shopper.db";

    //TODO: Cria a tabela user:
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String TABLE_PRODUTO = "produtos";

    private static final String COLUMN_PRODUTO_ID = "id_produto";
    private static final String COLUMN_PRODUTO_NOME = "nome_produto";
    private static final String COLUMN_PRODUTO_PRECO = "preco_produto";
    private static final String COLUMN_PRODUTO_BARCODE = "codigo_barras_produto";



    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ( " +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT " + " ); ";

    private String CREATE_PRODUTO_TABLE = "CREATE TABLE "+ TABLE_PRODUTO + " ( " + COLUMN_PRODUTO_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRODUTO_NOME + " TEXT, " + COLUMN_PRODUTO_PRECO +
            " REAL, " + COLUMN_PRODUTO_BARCODE + " INTEGER );";

    private String DROP_USER_TABLE  = "DROP TABLE IF EXISTS" + TABLE_USER;
    private String DROP_TABLE_PRODUTO = "DROP TABLE IF EXISTS" + TABLE_PRODUTO;

    private String SELECT_TABLE_PRODUTOS = "SELECT * FROM " + TABLE_PRODUTO;

    public DataBaseDAO(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_TABLE_PRODUTO);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = pegaDadosUser(user);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addProduto(Produto produto){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = pegaDadosProduto(produto);

        db.insert(TABLE_PRODUTO, null, values);
        db.close();
    }


    private ContentValues pegaDadosUser(User user){

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD,user.getPassword());
        return values;
    }

    private ContentValues pegaDadosProduto(Produto produto){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUTO_NOME, produto.getNome());
        values.put(COLUMN_PRODUTO_PRECO, produto.getPreco());
        values.put(COLUMN_PRODUTO_BARCODE, produto.getCodigoBarra());
        return values;
    }

    public boolean validarUser(String email, String password){
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " =? " + " AND " + COLUMN_USER_PASSWORD + " =? ";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean validarProduto(String codigodebarra){
        String[] columns = {COLUMN_PRODUTO_ID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_PRODUTO_BARCODE + " =? ";
        String[] selectionArgs = {codigodebarra};

        Cursor cursor = db.query(TABLE_PRODUTO, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public List<Produto> buscaProdutos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_TABLE_PRODUTOS, null);

        List<Produto> produtos = new ArrayList<Produto>();
        while (cursor.moveToNext()){
            Produto produto = new Produto();

            produto.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUTO_ID)));
            produto.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_NOME)));
            produto.setPreco(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_PRECO)));
            produto.setCodigoBarra(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_BARCODE)));

            produtos.add(produto);
        }
        cursor.close();
        return produtos;
    }

    public Produto buscaProdutoPeloBarcode(String barcode){
        Produto produto = null;

        SQLiteDatabase db = getReadableDatabase();

        String sql = SELECT_TABLE_PRODUTOS + " WHERE " +  COLUMN_PRODUTO_BARCODE + " = " + barcode;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){

            produto = new Produto();

            produto.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUTO_ID)));
            produto.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_NOME)));
            produto.setPreco(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_PRECO)));
            produto.setCodigoBarra(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUTO_BARCODE)));

        }
        return produto;
    }

    public void deletaProduto(Produto produto){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {(String.valueOf(produto.getId()))};
        db.delete(TABLE_PRODUTO, COLUMN_PRODUTO_ID + " = ?", params);

    }

    public void alteraProduto(Produto produto){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues dados = pegaDadosProduto(produto);
        String[]params = {String.valueOf(produto.getId())};
        db.update(TABLE_PRODUTO, dados, COLUMN_PRODUTO_ID + " = ?", params);
    }
}
