package com.example.blackjack;

public class Card {

    private String code;
    private String image;
    private final String cardBack = "https://www.deckofcardsapi.com/static/img/back.png";
    private String value;
    private String suit;

    public Card(String code, String image, String value, String suit) {
        this.code = code;
        this.image = image;
        this.value = value;
        this.suit = suit;
    }

    public String getCode() {
        return code;
    }

    public String getImage() {
        return image;
    }

    public String getCardBack() {
        return cardBack;
    }

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "code='" + code + '\'' +
                ", image='" + image + '\'' +
                ", cardBack='" + cardBack + '\'' +
                ", value='" + value + '\'' +
                ", suit='" + suit + '\'' +
                '}';
    }
}
