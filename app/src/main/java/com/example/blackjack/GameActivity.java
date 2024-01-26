package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}