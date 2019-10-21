package main;

class Card {

	private final int pwr;
	private final String suit;

	Card(int pwr, String suit){
		this.pwr = pwr;
		this.suit = suit;
	}

	int getPwr() {
		return pwr;
	}

	String getSuit() {
		return suit;
	}

}
