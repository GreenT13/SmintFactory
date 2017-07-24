package newcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that will create card objects element from Deck only.
 * Heavily relies on the fact that every card is unique, having only one instance of each cardId.
 */
public class CardManager {
	
	public final static List<Card> cardList;
	static Integer availableId;
	
	static {
		cardList = new ArrayList<Card>();
		availableId = 0;
		
		// Define all cards with properties here.
		addCard(new Card("Simpel rood", 1, 0, 1, Card.RED));
		addCard(new Card("Simpel groen", 0, 1, 1, Card.GREEN));
		addCard(new Card("Simpel geel", 1, 1, 1, Card.YELLOW));
		addCard(new Card("Speciaal2", 2, 2, 2, Card.RED));
		addCard(new Card("Simpel rood2", 1, 0, 1, Card.RED));
		addCard(new Card("Simpel groen2", 0, 1, 1, Card.GREEN));
		addCard(new Card("Simpel geel2", 1, 1, 1, Card.YELLOW));
		addCard(new Card("Speciaal2", 2, 2, 2, Card.RED));
		addCard(new Card("Simpel rood3", 1, 0, 1, Card.RED));
		addCard(new Card("Simpel groen3", 0, 1, 1, Card.GREEN));
		addCard(new Card("Simpel geel3", 1, 1, 1, Card.YELLOW));
		addCard(new Card("Speciaal3", 2, 2, 2, Card.RED));
	}

	private CardManager() {
		// This may not be instantiated.
	}
	
	private static void addCard(Card card) {
		// Hiermee wordt availableId zowel eentje opgehoogd als gezet.
		card.setCardId(availableId++);
		cardList.add(card);
	}
	
	public static Card retrieveCard(Integer cardId) {
		for (Card card : cardList) {
			if (card.getCardId() == cardId) {
				return card;
			}
		}
		
		// No card has been found with given id (which should not happen).
		return null;
	}
	
	public static Integer getNumberOfCards() {
		return cardList.size();
	}	
}
