package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

// TODO: create recycler view with adapter
// TODO: implement deck of cards api

// layout can be called hand_list_item
// recycler view will use card view widget look at recyclerV_dn_4_2
// adapter can be called HandRecViewAdapter

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        if (intent != null){
            int id = intent.getIntExtra("id", 100);
            Toast.makeText(this, "User id is: " + String.valueOf(id), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Couldnt get user data restrt aplication and log in again", Toast.LENGTH_SHORT).show();

        }

    }
}