package com.example.blackjackFirebase;

import java.util.List;

public class Deck {

    private boolean success;
    private String deck_id;
    private boolean shuffled;
    private List<Card> cards;
    private int remaining;

    public Deck(boolean success, String deck_id, boolean shuffled, List<Card> cards, int remaining) {
        this.success = success;
        this.deck_id = deck_id;
        this.shuffled = shuffled;
        this.cards = cards;
        this.remaining = remaining;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "success=" + success +
                ", deck_id='" + deck_id + '\'' +
                ", shuffled=" + shuffled +
                ", cards=" + cards +
                ", remaining=" + remaining +
                '}';
    }
}
