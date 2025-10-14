package com.codedifferently.lesson16.danielsonobject;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Card {
  private final Suit suit;
  private final int rank;

  public Card(Suit suit, int rank) {
    if (rank < 1 || rank > 15) {
      throw new IllegalArgumentException("Rank must be between 1 and 15");
    }
    this.suit = suit;
    this.rank = rank;
  }

  public Suit getSuit() {
    return suit;
  }

  public int getRank() {
    return rank;
  }

  public Card compareTo(Card other) {
    if (this.rank > other.rank) {
      return this;
    } else if (this.rank < other.rank) {
      return other;
    } else {
      return null; // They are equal
    }
  }

  @Override
  public String toString() {
    if (suit == Suit.NONE) {
      return "Joker";
    } else if (2 <= rank && rank <= 10) {
      return rank + " of " + suit.toString();
    } else {
      String face;
      switch (this.rank) {
        case 11 -> face = "Jack";
        case 12 -> face = "Queen";
        case 13 -> face = "King";
        default -> face = "Ace";
      }
      return face + " of " + suit.toString();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Card card)) return false;
    return rank == card.rank && suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suit, rank);
  }
}
