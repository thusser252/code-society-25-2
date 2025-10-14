package com.codedifferently.lesson16.danielsonobject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
  private final Random rand = new Random();
  private final String brand;
  private final ArrayList<Card> cards;
  private int max_size = 54;

  public Deck(String brand) {
    this.brand = brand;
    this.cards = new ArrayList<>();
    for (Suit suit : Suit.values()) {
      if (suit == Suit.NONE) {
        cards.add(new Card(Suit.NONE, 14)); // This will represent the Jokers.
        cards.add(new Card(Suit.NONE, 15));
      } else {
        for (int rank = 1; rank <= 13; rank++) {
          cards.add(new Card(suit, rank));
        }
      }
    }
  }

  public String getBrand() {
    return brand;
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  public Card draw() {
    if (cards.isEmpty()) {
      throw new DeckException("There are no Cards to draw");
    }
    return cards.removeFirst();
  }

  public int getSize() {
    return cards.size();
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void addToDeck(Card card) throws DeckException {
    if (cards.size() + 1 > max_size) {
      throw new DeckException("Deck limit reached");
    }
    cards.addFirst(card);
  }

  public void shuffleIntoDeck(Card card) throws DeckException {
    if (cards.size() + 1 > max_size) {
      throw new DeckException("Deck limit reached");
    }
    int insert = rand.nextInt(0, max_size);

    cards.add(insert, card);
  }

  public void removeJokers() throws JokerException {
    if (max_size == 52) {
      throw new JokerException("There are already no Jokers");
    }
    cards.removeIf(card -> card.getSuit() == Suit.NONE);
    max_size = 52;
  }

  public void addJokers() throws JokerException {
    if (max_size == 52) {
      max_size = 54;
      shuffleIntoDeck(new Card(Suit.NONE, 14));
      shuffleIntoDeck(new Card(Suit.NONE, 15));
    } else if (max_size == 54) {
      throw new JokerException("Jokers are already accounted for");
    }
  }
}
