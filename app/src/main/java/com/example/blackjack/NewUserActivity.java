package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


// TODO: watch imageview tutorial

// TODO: add images
// TODO: fix theme

public class NewUserActivity extends AppCompatActivity {

    private Button btnCreate, btnBack;
    private EditText editTextCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editTextCreateUser = findViewById(R.id.editTextCreateUser);
        btnCreate = findViewById(R.id.btnCreate);
        btnBack = findViewById(R.id.btnBack);

        DBHelper dbHelper= new DBHelper(NewUserActivity.this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first check database
                String userName = editTextCreateUser.getText().toString();

                if (userName.isEmpty()){
                    Toast.makeText(NewUserActivity.this, "Please enter user name", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (dbHelper.checkDB(userName)){
                        Toast.makeText(NewUserActivity.this, "User with this name already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        User user;
                        try {
                            user = new User(-1, userName, 100);

                        } catch (Exception e){
                            Toast.makeText(NewUserActivity.this, "Error inserting", Toast.LENGTH_SHORT).show();
                            user = new User(-1, "error", 0);
                        }

                        boolean success = dbHelper.addToDB(user);
                        if (success){
                            Toast.makeText(NewUserActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(NewUserActivity.this, "error inserting", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}