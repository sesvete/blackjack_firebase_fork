package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// responsible for loging in
// as of now buttons will sipmly lead to new screans

// for pictures get a picture of an ac anc a king or smth

// ne bo gesla!
// začetni sazlon anj vsebuje seznam z imeni uporabnikov in njihovimi točkami
// uporabnika se izberee s klikom naj
// poleg tega naj bo še gum za ustvarjanje novega uporabnika, ki pelje na NEWUSERactivity

// podatkovna baza
// Players.sql (or. smth)
// stolpci id (int primary key autoincrement), uIme (text / string), tocke (int)


// TODO: recycler view

// TODO: watch imageview tutorial
// TODO: implement database

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for now we just lead to the game activity
                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for now we just lead to the new user activity
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });


    }
}