package com.thgmobi.testeshopper.helper;

import android.widget.EditText;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.activities.ProductoActivity;
import com.thgmobi.testeshopper.model.Produto;


public class ProdutoHelper {
    
    public EditText campoNome, campoPreco, campoCodigoBarra;
    
    private Produto produto;

    public ProdutoHelper(ProductoActivity activity) {
        
        campoNome = activity.findViewById(R.id.tela_produto_edtnome);
        campoPreco = activity.findViewById(R.id.tela_produto_edtpreco);
        campoCodigoBarra = activity.findViewById(R.id.tela_produto_edtcode);

        produto = new Produto();

    }

    public Produto pegaProduto() {
        produto.setNome(campoNome.getText().toString());
        produto.setPreco(campoPreco.getText().toString());
        produto.setCodigoBarra(campoCodigoBarra.getText().toString());
        return produto;
    }

    public void preencheProduto(Produto produto){
        campoNome.setText(produto.getNome());
        campoPreco.setText(produto.getPreco());
        campoCodigoBarra.setText(produto.getCodigoBarra());
        this.produto = produto;
    }
}
