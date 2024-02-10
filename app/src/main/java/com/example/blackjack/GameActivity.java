package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;

// NEED TO ADD CALCULATION!!!!

// TODO: implement deck of cards api

// TODO: Functioning Buttons
// TODO: Drawing cards Methods
// TODO: Implement database
// TODO: calculations
// TODO: end of game procedures

// TODO: Dealers second card needs to be hidden
// TODO: dealer draws cars on clicking hold - for now draw till he has 5 cards in hand
// layout can be called hand_list_item
// recycler view will use card view widget look at recyclerV_dn_4_2
// adapter can be called HandRecViewAdapter

// buttons will have two modes START/HIT; STOP/HOLD

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerDealerHand, recyclerPlayerHand;
    private ArrayList<Card> dealerHand, playerHand;
    private Button btnStart, btnStop;
    private String shuffleUrl, deckID;
    private TextView txtPlayerSumSum, txtDealerSumSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        if (intent != null){
            int id = intent.getIntExtra("id", 100);
        }
        else {
            Toast.makeText(this, "Couldn't get user data restart application and log in again", Toast.LENGTH_SHORT).show();

        }
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        recyclerDealerHand = findViewById(R.id.recyclerDealerHand);
        recyclerPlayerHand = findViewById(R.id.recyclerPlayerHand);

        DealerHandRecViewAdapter dealerAdapter = new DealerHandRecViewAdapter(this);
        PlayerHandRecViewAdapter playerAdapter = new PlayerHandRecViewAdapter(this);
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();

        GameMethod gameMethod = new GameMethod(0, 0, 0);

        shuffleUrl = "https://www.deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";
        playerAdapter.setPlayerHand(playerHand);
        recyclerPlayerHand.setAdapter(playerAdapter);
        dealerAdapter.setDealerHand(dealerHand);
        recyclerDealerHand.setAdapter(dealerAdapter);

        recyclerDealerHand.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerPlayerHand.setLayoutManager(new GridLayoutManager(this, 4));

        txtPlayerSumSum = findViewById(R.id.txtPlayerSumSum);
        txtDealerSumSum = findViewById(R.id.txtDealerSumSum);

        txtPlayerSumSum.setText(String.valueOf(gameMethod.getPlayerSum()));
        txtDealerSumSum.setText(String.valueOf(gameMethod.getDealerSum()));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().equals("Start")) {
                    // we need to make sure we got the id
                    gameMethod.shuffleDeck(shuffleUrl, GameActivity.this, new GameMethod.ShuffleCallback() {
                        @Override
                        public void onShuffleComplete(String ID) {
                            deckID = ID;
                            gameMethod.drawCard(deckID, "2", playerHand, true, GameActivity.this, new GameMethod.DrawCardCallback() {
                                @Override
                                public void onDrawComplete(ArrayList<Card> hand) {
                                    recyclerPlayerHand.setAdapter(playerAdapter);
                                    txtPlayerSumSum.setText(String.valueOf(gameMethod.getPlayerSum()));

                                }

                                @Override
                                public void onDrawError(VolleyError error) {
                                    Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            gameMethod.drawCard(deckID, "2", dealerHand, false, GameActivity.this, new GameMethod.DrawCardCallback() {
                                @Override
                                public void onDrawComplete(ArrayList<Card> hand) {
                                    recyclerDealerHand.setAdapter(dealerAdapter);
                                    txtDealerSumSum.setText(String.valueOf(gameMethod.getDealerSum()));

                                }

                                @Override
                                public void onDrawError(VolleyError error) {
                                    Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                            btnStart.setText("Hit");
                            btnStop.setText("Hold");
                        }

                        @Override
                        public void onShuffleError(String errorMessage) {
                            Toast.makeText(GameActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // dealer draws two cards
                    // player draws two cards
                    // check points
                    // text se spremeni v Hit
                } else if (btnStart.getText().toString().equals("Hit")){
                    gameMethod.drawCard(deckID, "1", playerHand, true, GameActivity.this, new GameMethod.DrawCardCallback() {
                        @Override
                        public void onDrawComplete(ArrayList<Card> hand) {
                            recyclerPlayerHand.setAdapter(playerAdapter);
                            txtPlayerSumSum.setText(String.valueOf(gameMethod.getPlayerSum()));
                        }

                        @Override
                        public void onDrawError(VolleyError error) {
                            Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    // player draws card
                    // check points
                } else{
                    Toast.makeText(GameActivity.this, "Error, please restart the application", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStop.getText().toString().equals("Stop")){
                    // back to login screen
                    Intent stop = new Intent(GameActivity.this, LoginActivity.class);
                    startActivity(stop);
                } else if (btnStop.getText().toString().equals("Hold")) {
                    // dealer draws cards
                } else{
                    Toast.makeText(GameActivity.this, "Error, please restart the aplication", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}