package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

// TODO: implement deck of cards api

// TODO: Functioning Buttons
// TODO: Drawing cards Methods
// TODO: Implement database
// TODO: calculations
// TODO: end of game procedures
// layout can be called hand_list_item
// recycler view will use card view widget look at recyclerV_dn_4_2
// adapter can be called HandRecViewAdapter

// buttons will have two modes START/HIT; STOP/HOLD

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerDealerHand, recyclerPlayerHand;
    private ArrayList<Card> dealerHand, playerHand;

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
            Toast.makeText(this, "Couldnt get user data restart application and log in again", Toast.LENGTH_SHORT).show();

        }

        recyclerDealerHand = findViewById(R.id.recyclerDealerHand);
        recyclerPlayerHand = findViewById(R.id.recyclerPlayerHand);

        DealerHandRecViewAdapter dealerAdapter = new DealerHandRecViewAdapter(this);
        PlayerHandRecViewAdapter playerAdapter = new PlayerHandRecViewAdapter(this);
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();

        // for now we simply add 5 cards in each hand
        dealerHand.add(new Card("2H", "https://deckofcardsapi.com/static/img/2H.png", "2", "HEARTS"));
        dealerHand.add(new Card("7H", "https://deckofcardsapi.com/static/img/7H.png", "7", "HEARTS"));
        dealerHand.add(new Card("QH", "https://deckofcardsapi.com/static/img/QH.png", "Q", "HEARTS"));
        dealerHand.add(new Card("9H", "https://deckofcardsapi.com/static/img/9H.png", "9", "HEARTS"));
        dealerHand.add(new Card("5H", "https://deckofcardsapi.com/static/img/5H.png", "5", "HEARTS"));

        dealerAdapter.setDealerHand(dealerHand);
        recyclerDealerHand.setAdapter(dealerAdapter);


        playerHand.add(new Card("KC", "https://deckofcardsapi.com/static/img/KC.png", "K", "CLUBS"));
        playerHand.add(new Card("7C", "https://deckofcardsapi.com/static/img/7C.png", "7", "CLUBS"));
        playerHand.add(new Card("AC", "https://deckofcardsapi.com/static/img/AC.png", "A", "CLUBS"));
        playerHand.add(new Card("9C", "https://deckofcardsapi.com/static/img/9C.png", "9", "CLUBS"));
        playerHand.add(new Card("5C", "https://deckofcardsapi.com/static/img/5C.png", "5", "CLUBS"));

        playerAdapter.setPlayerHand(playerHand);
        recyclerPlayerHand.setAdapter(playerAdapter);

        recyclerDealerHand.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerPlayerHand.setLayoutManager(new GridLayoutManager(this, 4));


    }
}