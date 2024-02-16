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

// TODO: Implement database

// TODO: Get player points from database and update them on game end

// TODO: end of game procedures
// TODO: use strings.xml file
// recycler view will use card view widget look at recyclerV_dn_4_2
// adapter can be called HandRecViewAdapter

public class GameActivity extends AppCompatActivity {
    private int id;
    private RecyclerView recyclerDealerHand, recyclerPlayerHand;
    private ArrayList<Card> dealerHand, playerHand;
    private Button btnStart, btnStop;
    private String shuffleUrl, deckID;
    private TextView txtTotalPoints, txtPlayerSumSum, txtDealerSumSum;
    private DealerHandRecViewAdapter dealerAdapter;
    private PlayerHandRecViewAdapter playerAdapter;
    private GameMethod gameMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        if (intent != null){
            id = intent.getIntExtra("id", 100);
        }
        else {
            Toast.makeText(this, "Couldn't get user data restart application and log in again", Toast.LENGTH_SHORT).show();

        }

        DBHelper dbHelper = new DBHelper(GameActivity.this);

        txtTotalPoints = findViewById(R.id.txtTotalPoints);

        txtTotalPoints.setText(String.valueOf(dbHelper.getPointsFromDB(id)));

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        recyclerDealerHand = findViewById(R.id.recyclerDealerHand);
        recyclerPlayerHand = findViewById(R.id.recyclerPlayerHand);
        gameMethod = new GameMethod(0, 0, 0);

        shuffleUrl = "https://www.deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1";

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
                    disableButtons();
                    setInitialGameState();
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
                                    txtDealerSumSum.setText(String.valueOf(gameMethod.getDealerRevealedValue()));
                                    btnStart.setText("Hit");
                                    btnStop.setText("Hold");
                                    enableButtons();
                                    if (gameMethod.getPlayerSum() == 21) {
                                        revealDealerSecondCard();
                                        gameMethod.gameResolution(btnStart, btnStop, GameActivity.this);
                                    }
                                }
                                @Override
                                public void onDrawError(VolleyError error) {
                                    Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onShuffleError(String errorMessage) {
                            Toast.makeText(GameActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (btnStart.getText().toString().equals("Hit")){
                    gameMethod.drawCard(deckID, "1", playerHand, true, GameActivity.this, new GameMethod.DrawCardCallback() {
                        @Override
                        public void onDrawComplete(ArrayList<Card> hand) {
                            recyclerPlayerHand.setAdapter(playerAdapter);
                            txtPlayerSumSum.setText(String.valueOf(gameMethod.getPlayerSum()));
                            if (gameMethod.getPlayerSum() == 21){
                                resolveDealerHand();
                            } else if (gameMethod.getPlayerSum() > 21) {
                                revealDealerSecondCard();
                                gameMethod.gameResolution(btnStart, btnStop, GameActivity.this);
                            }
                        }

                        @Override
                        public void onDrawError(VolleyError error) {
                            Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                    resolveDealerHand();
                } else{
                    Toast.makeText(GameActivity.this, "Error, please restart the application", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resolveDealerHand(){
        revealDealerSecondCard();
        if ((gameMethod.getDealerSum() < gameMethod.getPlayerSum()) && gameMethod.getDealerSum() < 18){
            drawCardDealer();
        }
        else {
            gameMethod.gameResolution(btnStart, btnStop, GameActivity.this);
        }
    }

    private void drawCardDealer() {
        gameMethod.drawCard(deckID, "1", dealerHand, false, GameActivity.this, new GameMethod.DrawCardCallback() {
            @Override
            public void onDrawComplete(ArrayList<Card> hand) {
                revealDealerSecondCard();
                if ((gameMethod.getDealerSum() < gameMethod.getPlayerSum()) && gameMethod.getDealerSum() < 18){
                    drawCardDealer();
                }
                else{
                    gameMethod.gameResolution(btnStart, btnStop, GameActivity.this);
                }
            }
            @Override
            public void onDrawError(VolleyError error) {
                Toast.makeText(GameActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void revealDealerSecondCard() {
        dealerAdapter.revealSecondCard();
        txtDealerSumSum.setText(String.valueOf(gameMethod.getDealerSum()));
    }
    private void enableButtons() {
        btnStart.setEnabled(true);
        btnStop.setEnabled(true);
    }
    private void disableButtons() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
    }
    private void setInitialGameState(){
        gameMethod.setPlayerSum(0);
        gameMethod.setDealerSum(0);
        gameMethod.setDealerRevealedValue(0);
        dealerAdapter = new DealerHandRecViewAdapter(GameActivity.this);
        playerAdapter = new PlayerHandRecViewAdapter(GameActivity.this);
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
        playerAdapter.setPlayerHand(playerHand);
        recyclerPlayerHand.setAdapter(playerAdapter);
        dealerAdapter.setDealerHand(dealerHand);
        recyclerDealerHand.setAdapter(dealerAdapter);
    }
}