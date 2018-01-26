package com.thgmobi.testeshopper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.activities.HomeActivity;
import com.thgmobi.testeshopper.model.Produto;

import java.util.List;



public class ProdutosAdapter extends BaseAdapter{

    private final List<Produto> produtos;
    private final Context context;

    public ProdutosAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produto produto = produtos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (convertView == null){

            view = inflater.inflate(R.layout.layout_lista_produtos, parent, false);

        }

        TextView campoNomeProduto = view.findViewById(R.id.layout_lista_txtnomeproduto);
        campoNomeProduto.setText(produto.getNome());

        TextView campoPreco = view.findViewById(R.id.layout_lista_txtpreco);
        campoPreco.setText(produto.getPreco());

        TextView campoBarcode = view.findViewById(R.id.layout_lista_txtbarcode);
        campoBarcode.setText(produto.getCodigoBarra());



        return view;
    }
}
