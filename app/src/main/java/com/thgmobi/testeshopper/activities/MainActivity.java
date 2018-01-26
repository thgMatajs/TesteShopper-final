package com.thgmobi.testeshopper.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.dao.DataBaseDAO;

public class MainActivity extends AppCompatActivity  {

    private Context context;
    private DataBaseDAO dataBaseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getBaseContext();

        dataBaseDAO = new DataBaseDAO(this);


        Button btnRegistrar = findViewById(R.id.tela_login_btn_cadastrar);
        Button btnLogar = findViewById(R.id.tela_login_btn_logar);
        final EditText edtEmail = findViewById(R.id.tela_login_edtemail);
        final EditText edtSenha = findViewById(R.id.tela_login_edtsenha);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiproRegisto = new Intent(context, RegisterActivity.class);

                startActivity(intentVaiproRegisto);

            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString();
                String password = edtSenha.getText().toString();

                boolean validarUser = dataBaseDAO.validarUser(email, password);

                if (validarUser == true){
                    Intent intentVaipraPrincipal = new Intent(context, HomeActivity.class);
                    startActivity(intentVaipraPrincipal);
                    finish();

                }else {

                    Toast.makeText(context, "E-mail ou senha invalidos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
