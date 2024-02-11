package com.example.blackjack;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Check for total sum before drawing card
// TODO: hide second card drawn by dealer

// for the image we will probably need to

public class GameMethod {

    private int playerSum;
    private int dealerSum;
    private int dealerRevealedValue;

    public GameMethod(int playerSum, int dealerSum, int dealerRevealedValue) {
        this.playerSum = playerSum;
        this.dealerSum = dealerSum;
        this.dealerRevealedValue = dealerRevealedValue;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public void setPlayerSum(int playerSum) {
        this.playerSum = playerSum;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public void setDealerSum(int dealerSum) {
        this.dealerSum = dealerSum;
    }

    public int getDealerRevealedValue() {
        return dealerRevealedValue;
    }

    public void setDealerRevealedValue(int dealerRevealedValue) {
        this.dealerRevealedValue = dealerRevealedValue;
    }

    public interface ShuffleCallback {
        void onShuffleComplete(String ID);
        void onShuffleError(String errorMessage);
    }

    public interface DrawCardCallback {
        void onDrawComplete(ArrayList<Card> hand);
        void onDrawError(VolleyError error);
    }

    public void shuffleDeck(String shuffleUrl, Context context, ShuffleCallback callback){
        Gson gson = new Gson();
        StringRequest request= new StringRequest(Request.Method.GET, shuffleUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Deck deck = gson.fromJson(response, Deck.class);
                String ID = deck.getDeck_id();
                // Pass the deckID to the callback
                callback.onShuffleComplete(ID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                // Pass an error message to the callback
                callback.onShuffleError("Error occurred during shuffle");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        queue.start();
    }

    public void drawCard(String deckID, String numberDrawn, ArrayList<Card> hand, boolean isPlayer, Context context, DrawCardCallback callback){
        Gson gson = new Gson();
        String drawUrl = "https://www.deckofcardsapi.com/api/deck/" + deckID + "/draw/?count=" + numberDrawn;

        StringRequest request = new StringRequest(Request.Method.GET, drawUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Deck deck = gson.fromJson(response, Deck.class);
                List<Card> cards= deck.getCards();
                for (Card card: cards){
                    String code = card.getCode();
                    String image = card.getImage();
                    String value = card.getValue();
                    String suit = card.getSuit();

                    hand.add(new Card(code, image, value, suit));
                    if (isPlayer){
                        setPlayerSum(updateCardSum(value, getPlayerSum()));
                    }
                    // here we have to check how may cars are in hand
                    else{
                        setDealerSum(updateCardSum(value, getDealerSum()));
                    }
                }
                callback.onDrawComplete(hand);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onDrawError(error);

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        queue.start();
    }

    public void gameResolution(){
        // method designed for comparing sums and deciding winner
    }

    private int updateCardSum(String cardValue, int totalSum) {
        String[] jqk = {"JACK", "QUEEN", "KING"};
        if (Arrays.asList(jqk).contains(cardValue)){
            totalSum += 10;
        } else if (cardValue.equals("ACE")) {
            if (totalSum < 11){
                totalSum += 11;
            } else {
                totalSum += 1;
            }
        } else {
            totalSum += Integer.parseInt(cardValue);
        }
        return totalSum;
    }
}
