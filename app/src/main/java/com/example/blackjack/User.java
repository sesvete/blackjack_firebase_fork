package com.example.blackjack;

public class User {
    int id;
    String userName;
    int points;

    public User(int id, String userName, int points) {
        this.id = id;
        this.userName = userName;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", points=" + points +
                '}';
    }
}

