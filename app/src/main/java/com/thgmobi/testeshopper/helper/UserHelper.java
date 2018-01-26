package com.thgmobi.testeshopper.helper;


import android.widget.EditText;

import com.thgmobi.testeshopper.R;
import com.thgmobi.testeshopper.activities.RegisterActivity;
import com.thgmobi.testeshopper.model.User;



public class UserHelper {


    private EditText edtName, edtEmail, edtPassword;

    private User user;


    public UserHelper(RegisterActivity activity) {

        edtName = activity.findViewById(R.id.activity_register_edtname);
        edtEmail = activity.findViewById(R.id.activity_register_edtemail);
        edtPassword = activity.findViewById(R.id.activity_register_edtpassword);

        user = new User();

    }

    public User pegaUser(){

        user.setName(edtName.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());

        return user;
    }


}
