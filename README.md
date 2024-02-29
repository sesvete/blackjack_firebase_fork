# Blackjack android app

Written in Java, using Android dev studio

App uses Firebase for authentication and cloud storage

Cards are acquired through the deck of cards api:
https://www.deckofcardsapi.com/

The app has three activities

## Login activity

login into an existing account
if we don't have an account, we can create a new one through the new user activity

recycler view

constraint layout

## New user activity

responsible for creating new accounts

The purpose of the first two activities is to have an excuse to use an database

constraint layout

## Game activity

The activity where the game is played

relative layout

## Required methods

### Game activity

shuffle deck
draw cards (first 2 card, then 1)

## Connect to firebase

copy google-services.json into app folder
