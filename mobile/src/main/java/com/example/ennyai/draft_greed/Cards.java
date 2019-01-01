package com.example.ennyai.draft_greed;

public class Cards {

    private String suit;
    private int value;
    private String filename;
    private int owner;
    private String pair;

    public Cards(String suit, int value, String filename, int owner, String pair) {
        this.suit = suit;
        this.value = value;
        this.filename = filename;
        this.owner = owner;
        this.pair = pair;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
