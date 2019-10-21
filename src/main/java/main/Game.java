package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

	public static void main(String[] args) {
		List<Card> deck = createDeck();

		Player p1 = new Player("Strahil");
		Player p2 = new Player("Pesho");

		dealCards(p1, p2, deck);

		System.out.println("===" + p1.getName() + " VS " + p2.getName() + "===");

		Player winner = playWar(p1, p2);
		if(winner == null) System.out.println("It's a tie!");
		else System.out.println("Winner: " + winner.getName());



	}

	private static Player playWar(Player p1, Player p2)
	{
		List<Card> pot = new ArrayList<>();

		while(p1.isGameOver() && p2.isGameOver())
		{
			p1.printHand();
			p2.printHand();
			Card p1Card = p1.getNextCard();
			Card p2Card = p2.getNextCard();
			pot.add(p1Card);
			pot.add(p2Card);

			if (p1Card.getPwr() == p2Card.getPwr()) {
				war(p1, p2, pot, 1);
				pot.clear();
				continue;
			}

			if (p1Card.getPwr() > p2Card.getPwr())
			{
				p1.takeWinnings(pot);
			} else
			{
				p2.takeWinnings(pot);
			}
		}

		Player winner = null;

		if(p1.getWon() > p2.getWon()) winner = p1;
		if(p2.getWon() > p1.getWon()) winner = p2;

		return winner;
	}

	private static boolean war(Player p1, Player p2, List<Card> pot, int round)
	{

		Card p1Card;
		Card p2Card;
		if (p1.isGameOver())
		{
			p1Card = p1.getNextCard();
			p2Card = p2.getNextCard();
			pot.add(p1Card);
			pot.add(p2Card);
		}
		else
		{
			return false;
		}

		if (round >= 3)
		{
			if(p1Card.getPwr() == p2Card.getPwr())
			{
				war(p1, p2, pot, round+1);
			}
			else
			{
				return winWar(p1, p2, p1Card, p2Card, pot);
			}
		}
		else
		{
			if(!war(p1, p2, pot, round+1))
			{
				if (p1Card.getPwr() == p2Card.getPwr()) return false;
				else
				{
					return winWar(p1, p2, p1Card, p2Card, pot);
				}
			}
		}

		return true;
	}

	private static boolean winWar(Player p1, Player p2, Card p1Card, Card p2Card, List<Card> pot)
	{
		Player winner = p1Card.getPwr() > p2Card.getPwr() ? p1 : p2;
		winner.takeWinnings(pot);
		pot.clear();
		return true;
	}

	private static void dealCards(Player p1, Player p2, List<Card> deck)
	{
		p1.addToHand(deck.subList(0, 26));
		p2.addToHand(deck.subList(26, 52));
	}

	private static List<Card> createDeck()
	{
		List<Card> deck = new ArrayList<>(52);
		fillDeck(deck); // filling the deck with cards to deal to the players;
		Collections.shuffle(deck);
		return deck;
	}

	private static void fillDeck(List<Card> deck){
		String[] powers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		char[] suits = {'\u2660', '\u2665', '\u2666', '\u2663'};
		for (char suit : suits)
		{
			for (int j = 0; j < powers.length; j++)
			{
				deck.add(new Card(j, (powers[j] + suit)));
			}
		}
	}
}
