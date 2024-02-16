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

// playing card dimensions height: 89mm, width 64mm
// 1:1,391

// TODO: FIX THEME

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

        UserRecViewAdapter adapter = new UserRecViewAdapter();
        adapter.setUserList(userList);
        recViewLogin.setAdapter(adapter);

        recViewLogin.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new UserRecViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int user_id = userList.get(position).getId();
                Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                intent.putExtra("id", user_id);
                startActivity(intent);
            }
        });

        adapter.setOnItemLongClickListener(new UserRecViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                // delete user
                int user_id = userList.get(position).getId();
                dbHelper.deleteFromDB(user_id);
                adapter.setUserList(dbHelper.getList());

                Toast.makeText(LoginActivity.this, userList.get(position).getUserName() + " was deleted", Toast.LENGTH_SHORT).show();

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