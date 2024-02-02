package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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

// playing card dimensions height: 89mm, width 64mm
// 1:1,391


// TODO: watch imageview tutorial

// TODO: FIX THEME
// TODO: delete entry from database

public class LoginActivity extends AppCompatActivity {

    private Button btnNewUser;
    private RecyclerView recViewLogin;
    private ArrayList<User> userList;
    private ImageView imageViewLoginAce, imageViewLoginKing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageViewLoginAce = findViewById(R.id.imageViewLoginAce);
        imageViewLoginKing = findViewById(R.id.imageViewLoginKing);

        Glide.with(this).asBitmap().load("https://deckofcardsapi.com/static/img/AH.png").into(imageViewLoginAce);
        Glide.with(this).asBitmap().load("https://deckofcardsapi.com/static/img/KH.png").into(imageViewLoginKing);

        btnNewUser = findViewById(R.id.btnNewUser);
        recViewLogin = findViewById(R.id.recViewLogin);

        DBHelper dbHelper = new DBHelper(LoginActivity.this);

        userList = dbHelper.getList();

        // for testing manual insertion

        UserRecViewAdapter adapter = new UserRecViewAdapter();
        adapter.setUserList(userList);
        recViewLogin.setAdapter(adapter);

        recViewLogin.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new UserRecViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // for now we just lead to the game activity

                // for now we just check if it passes the user id

                int user_id = userList.get(position).getId();

                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                intent.putExtra("id", user_id);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new UserRecViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(LoginActivity.this, "id: " + String.valueOf(userList.get(position).getId()) +
                        ", Username: " + userList.get(position).getUserName() + ", points: " + String.valueOf(userList.get(position).getPoints()),
                        Toast.LENGTH_SHORT).show();
            }
        });


        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);
            }
        });

    }
}