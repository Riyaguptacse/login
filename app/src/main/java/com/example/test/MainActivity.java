package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText et_email,et_password;
    Button buttonLogin;
    DatabaseHelper MyDatabaseHelper;
    String email,password;
    TextView tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        buttonLogin=findViewById(R.id.button_login);
        tv_register=findViewById(R.id.tv_register);

        MyDatabaseHelper  = new DatabaseHelper(this);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=et_email.getText().toString();
                password=et_password.getText().toString();
                if (email.equals("") && password.equals("")) {
                    Toast.makeText(getApplicationContext(),"Please Insert Email and password",Toast.LENGTH_LONG).show();
                } else {
                    int status =Integer.parseInt( MyDatabaseHelper.getLoginData(email, password));
                    if (status>0) {
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not Registerd!", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), registration.class);
                startActivity(i);
            }
        });
    }
}
