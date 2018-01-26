package com.thgmobi.testeshopper.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.dao.DataBaseDAO;
import com.thgmobi.testeshopper.helper.ProdutoHelper;
import com.thgmobi.testeshopper.model.Produto;



public class ProductoActivity extends AppCompatActivity {

    private ProdutoHelper helper;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        helper = new ProdutoHelper(this);
        context = getBaseContext();

        //TODO: Recebe o valor da leitura do codigo de barras caso no produto seja null
        final Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        helper.campoCodigoBarra.setText(barcode);
        Produto produto = (Produto) intent.getSerializableExtra("produto");
        //
        if (produto != null) {
            helper.preencheProduto(produto);
        }

        Button btnSalvar = findViewById(R.id.tela_produto_btnsalvar);
        Button btnCancelar = findViewById(R.id.tela_produto_btncancelar);

        //TODO: Salva o produto no db
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produto produto = helper.pegaProduto();

                DataBaseDAO dao = new DataBaseDAO(context);

                if (produto.getId() != 0){

                    dao.alteraProduto(produto);
                    Toast.makeText(context, "Alteração efetuado com sucesso.", Toast.LENGTH_SHORT).show();
                }else {
                    dao.addProduto(produto);
                    Toast.makeText(context, "Produto "+ produto.getNome()+" cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }

                dao.close();
                finish();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
