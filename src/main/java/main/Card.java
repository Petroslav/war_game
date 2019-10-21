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

	@Override
	public String toString()
	{
		return suit;
	}
}
