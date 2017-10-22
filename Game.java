package War_Game;

import java.util.Random;

public class Game {

	public static void main(String[] args) {
		Card[] deck = new Card[52]; // creating a deck
		Card[] field = new Card[52]; // creating a field for the cards to be played in
		fillDeck(deck); // filling the deck with cards to deal to the players;
		shuffleDeck(deck); // shuffling the deck;
		Player p1 = new Player("Strahil"); //creating players
		Player p2 = new Player("Pesho");
		Player winner = null;
		
		for (int i = 0; i < deck.length; i++) {  //dealing cards to the players
			if(i%2 == 0) p2.addToHand(deck[i]);
			else p1.addToHand(deck[i]);
		}
		System.out.println("===" + p1.getName() + " VS " + p2.getName() + "===");
		do{
			// Visualizing the hands of the players
			p1.printHand(); 
			p2.printHand();
			System.out.println(p1.getNextCard().getSuit() + " VS " + p2.getNextCard().getSuit());	// Showing the current match-up
			//Adding the cards to the field
			addToPot(field, p1.getNextCard());
			addToPot(field, p2.getNextCard());
			//Comparing the cards
			if(p1.getPower() > p2.getPower()) p1.takeCards(field);
			if(p2.getPower() > p1.getPower()) p2.takeCards(field);
			if(p2.getPower() == p1.getPower()){
				System.out.println("===WAR!===");
				war(p1, p2, winner, field);
			}
			//Removing the cards from play
			p1.playCard();
			p2.playCard();
		}while(!(end(p1, p2)));
		
		if(p1.getWon().size() == p2.getWon().size()) System.out.println("It's a TIE?!");
		else{
			System.out.println((p1.getWon().size() > p2.getWon().size() ? p1.getName() : p2.getName()) + " wins the game!");
		}
		
	}
	
	public static void shuffleDeck(Card[] deck){
		Random rnd = new Random();
	       for (int i = deck.length - 1; i > 0; i--)
	       {
	          int index = rnd.nextInt(i + 1);
	          Card temp = deck[index];
	          deck[index] = deck[i];
	          deck[i] = temp;

	       }
	}
	public static void fillDeck(Card[] deck){
		String[] pwrs = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		char[] suits = {'\u2660', '\u2665', '\u2666', '\u2663'};
		int cnt = 0;
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < pwrs.length; j++) {
				deck[cnt] = new Card(j, (pwrs[j]+((char) suits[i])));
				cnt++;
				if(cnt == deck.length) break;
			}
		}
	}
	public static void addToPot(Card[] pot, Card c){
		for (int i = 0; i < pot.length; i++) {
			if(pot[i] == null){
				pot[i] = c;
				break;
			}
		}
	}	
	public static void war(Player p1, Player p2, Player win, Card[] field){
		//Checking if there are any cards left to play
		if(end(p1, p2)){
			System.out.println("No more cards! War ends in a draw.");
			return;
		}
		// adding cards to the field
		addToPot(field, p1.getNextCard());
		addToPot(field, p2.getNextCard());
		//Deciding round winner in case of last card, this is done for each round
		win = winner(p1,p2);
		p1.playCard();
		p2.playCard();
		if(end(p1, p2)){
			if(win == null) System.out.println("No more cards! War ends in a draw");
			else win.takeCards(field);
			return;
		}
		addToPot(field, p1.getNextCard());
		addToPot(field, p2.getNextCard());
		win = winner(p1,p2);
		p1.playCard();
		p2.playCard();
		if(end(p1,p2)){
			win.takeCards(field);
			return;
		}
		roundOfWar(p1, p2, field);
	}
	public static void roundOfWar(Player p1, Player p2, Card[] field){
		//playing a simple round to decide the war, keep on playing until there is a difference in the card value or players run out of cards
		addToPot(field, p1.getNextCard());
		addToPot(field, p2.getNextCard());
		if(p1.getPower() > p2.getPower()) p1.takeCards(field);
		if(p2.getPower() > p1.getPower()) p2.takeCards(field);
		if(p2.getPower() == p1.getPower()){
			if(end(p1, p2)){
				System.out.println("No more cards! War ends in a draw.");
				return;
			}else roundOfWar(p1, p2, field);
		}
		p1.playCard();
		p2.playCard();
	}
	public static boolean end(Player p1, Player p2){
		if(p1.getNextCard() == null || p2.getNextCard() == null) return true;
		else return false;
	}	
	public static Player winner(Player p1, Player p2){
		if(p1.getPower() > p2.getPower()) return p1;
		if(p1.getPower() < p2.getPower()) return p2;
		else return null;
	}
}
