package com.thgmobi.testeshopper.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.dao.DataBaseDAO;
import com.thgmobi.testeshopper.helper.UserHelper;
import com.thgmobi.testeshopper.model.User;


public class RegisterActivity extends AppCompatActivity {

    private UserHelper helper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = getBaseContext();
        helper = new UserHelper(this);


        Button btnRegistar = findViewById(R.id.activity_registar_btnregister);
        Button btnCancelar = findViewById(R.id.activity_registar_btnrcancel);

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = helper.pegaUser();
                DataBaseDAO dao = new DataBaseDAO(context);


                dao.addUser(user);
                finish();
                Toast.makeText(context, "Usuário " + user.getName() + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
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
