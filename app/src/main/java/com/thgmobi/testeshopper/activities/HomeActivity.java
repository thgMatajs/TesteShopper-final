package com.thgmobi.testeshopper.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.adapter.ProdutosAdapter;
import com.thgmobi.testeshopper.dao.DataBaseDAO;
import com.thgmobi.testeshopper.model.Produto;

import java.util.List;


public class HomeActivity extends AppCompatActivity {


    private ListView listaProdutos;

    String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listaProdutos = findViewById(R.id.lv_produtos);


        carregaListaProdutos();

        listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Produto produto = (Produto) listaProdutos.getItemAtPosition(position);
                Intent intentVaiParaTelaProduto = new Intent(HomeActivity.this, ProductoActivity.class);
                intentVaiParaTelaProduto.putExtra("produto", produto);
                startActivity(intentVaiParaTelaProduto);
            }
        });

        FloatingActionButton fbScan = findViewById(R.id.home_fb_scan);
        final Activity activity = this;

        fbScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Scan(activity);

            }
        });

        registerForContextMenu(listaProdutos);

    }
    //TODO: Metodo para iniciar a Activity de leitura de codigo de barra
    private void Scan(Activity activity) {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Centralize o codigo de barra no centro da tela");
        integrator.setCameraId(0);
        integrator.initiateScan();
        integrator.setOrientationLocked(true);
    }
    //TODO: Pega o valor da leitura do codigo de barra
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        DataBaseDAO dataBaseDAO = new DataBaseDAO(this);

        if (result != null){

            if (result.getContents() != null) {
                barcode = result.getContents();

                boolean validarProduto = dataBaseDAO.validarProduto(barcode);

                if (validarProduto == true){
                    Produto produto = dataBaseDAO.buscaProdutoPeloBarcode(barcode);
                    Intent intentVaiParaTelaProduto = new Intent(HomeActivity.this, ProductoActivity.class);
                    intentVaiParaTelaProduto.putExtra("produto", produto);
                    startActivity(intentVaiParaTelaProduto);
                }else{
                    Intent intentVaiParaTelaProduto = new Intent(HomeActivity.this, ProductoActivity.class);
                    intentVaiParaTelaProduto.putExtra("barcode", barcode);
                    startActivity(intentVaiParaTelaProduto);
                }

            }else{
                Toast.makeText(this, "Erro ao Escanear", Toast.LENGTH_SHORT).show();
            }

        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //TODO: Metodo para manter a lista de produtos atualizada
    private void carregaListaProdutos() {
        DataBaseDAO dao = new DataBaseDAO(this);
        List<Produto> produtos = dao.buscaProdutos();
        dao.close();

        ProdutosAdapter adapter = new ProdutosAdapter(this, produtos);
        listaProdutos.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        carregaListaProdutos();
    }

    //TODO: Opção de deletar um produto
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Produto produto = (Produto) listaProdutos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                DataBaseDAO dataBaseDAO = new DataBaseDAO(HomeActivity.this);
                dataBaseDAO.deletaProduto(produto);
                dataBaseDAO.close();
                Toast.makeText(HomeActivity.this, produto.getNome() + "deletado com sucesso.", Toast.LENGTH_SHORT).show();
                carregaListaProdutos();
                return false;
            }
        });
    }
}
