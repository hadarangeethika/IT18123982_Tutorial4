package com.example.tutorial4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial4.Database.DBHandler;

public class MainActivity extends AppCompatActivity {
    EditText userName, password;
    Button btnSelectAll, btnAdd, btnSignIn, btnDelete, btnUpdate;
    DBHandler myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBHandler(this);
        userName = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        btnSelectAll = findViewById(R.id.btn_select_all);
        btnAdd = findViewById(R.id.btn_add);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.addInfo(userName.getText().toString(), password.getText().toString());
                Toast.makeText(MainActivity.this, "A new user added!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
