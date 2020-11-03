package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registration extends AppCompatActivity {
    EditText et_email,et_password,et_name,et_mobile;
    Button buttonRegister;
    DatabaseHelper MyDatabaseHelper;
    String email,password,name,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        buttonRegister=findViewById(R.id.button_register);
        et_mobile=findViewById(R.id.et_mobile);
        et_name=findViewById(R.id.et_name);

        MyDatabaseHelper=  new DatabaseHelper(this);;
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=et_email.getText().toString();
                password=et_password.getText().toString();
                name=et_name.getText().toString();
                mobile=et_mobile.getText().toString();

                if (email.equals(null) && password.equals(null)&&name.equals(null)&&mobile.equals(null)) {
                    Toast.makeText(getApplicationContext(),"Please Insert Email and password",Toast.LENGTH_LONG).show();
                } else {
                    boolean status = MyDatabaseHelper.addUser(name,email,mobile,password);
                    if (status) {
                        Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not Registerd!", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
    }
}
