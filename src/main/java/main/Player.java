package main;

import java.util.ArrayList;
import java.util.List;

class Player {

	private String name;
	private final List<Card> hand;
	private final List<Card> won;
	
	String getName() {
		return name;
	}

	Player (String name){
		this.name = name;
		this.hand = new ArrayList<>(26);
		this.won = new ArrayList<>(52);
	}
	
	void addToHand(List<Card> cards){
		hand.addAll(cards);
	}

	void takeWinnings(List<Card> pot)
	{
		won.addAll(pot);
		pot.clear();
	}

	boolean isGameOver()
	{
		return hand.size() == 0;
	}

	Card getNextCard(){
		return hand.remove(hand.size()-1);
	}

	void printHand(){
		System.out.println(getName() + "'s hand: " + hand);
	}

	int getWon() {
		return won.size();
	}

	int getHandSize() {
		return hand.size();
	}
	
	
}
