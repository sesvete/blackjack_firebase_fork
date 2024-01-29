package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// naj ima samo eno vnosno polje (za ime uporabnika)
// preveri se sintaksa, pa v podatkovni bazi, če uporabnik s tem imenom že ostaja
// gumb za kreacijo, gumb za nazaj


// TODO: watch imageview tutorial
// TODO: implement database

public class NewUserActivity extends AppCompatActivity {

    private Button btnCreate;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        btnCreate = findViewById(R.id.btnCreate);
        btnBack = findViewById(R.id.btnBack);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                startActivity(intent);
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