package model.card;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

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
		addCard(new Card("Assembler", 0, 1, 5, Card.YELLOW));
		addCard(new Card("Corporate HQ", 0, 0, 3, Card.RED)
		{	@Override
			public Integer determineExtraMoney(ObservableList<Card> plans, ObservableList<Card> buildings) {
				return plans.size();
			}
		});
		addCard(new Card("Wholesaler", 0, 1, 1, Card.AQUAMARINE));
		addCard(new Card("Crane", 0, 1, 2, Card.YELLOW));
		addCard(new Card("Lotto", 0, 2, 4, Card.AQUAMARINE));
		addCard(new Card("Mine", 1, 1, 2, Card.RED));
		addCard(new Card("Workshop", 1, 2, 3, Card.RED));
		addCard(new Card("Windmill", 0, 1, 1, Card.GREEN));
		addCard(new Card("Corporate HQ", 1, 0, 3, Card.RED));
		addCard(new Card("Bridge", 0, 0, 1, Card.GREEN) {
			@Override
			public Integer determineStars(ObservableList<Card> plans, ObservableList<Card> buildings) {
				if(buildings.contains(retrieveCard("Museum"))){
					return 1;
				} else {
					return stars;
				}
			}
		});
		addCard(new Card("Factory", 1, 3, 4, Card.RED));
		addCard(new Card("Co-op", 1, 1, 1, Card.RED));
		addCard(new Card("Plant", 2, 2, 5, Card.RED));
		addCard(new Card("Gallery", 0, 1, 4, Card.GREEN));
		addCard(new Card("Gardens", 0, 3, 3, Card.GREEN));
		addCard(new Card("Statue", 0, 2, 2, Card.GREEN));
		addCard(new Card("Stripmine", 3, 0, 4, Card.RED));
		addCard(new Card("Vault", 0, 2, 5, Card.YELLOW) {
			@Override
			public Integer determineStars(ObservableList<Card> plans, ObservableList<Card> buildings) {
				return plans.size()*2;
			}
		});
		addCard(new Card("Obelisk", 0, 1, 4, Card.YELLOW){
			@Override
			public Integer determineStars(ObservableList<Card> plans, ObservableList<Card> buildings) {
				return buildings.size();
			}
		});
		addCard(new Card("Museum", 0, 1, 2, Card.GREEN){
			@Override
			public Integer determineStars(ObservableList<Card> plans, ObservableList<Card> buildings) {
				int greens=0;
				for(Card card:buildings){
					if(card.getColor() == Card.GREEN){
						greens += 1;						
					}
				}
				stars = greens;
				return stars;
			}
		});
		addCard(new Card("Truck", 0, 1, 2, Card.YELLOW));
		addCard(new Card("Landfill", 0, 3, 3, Card.YELLOW){
			@Override
			public Integer determineStars(ObservableList<Card> plans, ObservableList<Card> buildings) {
				int greens=0;
				for(Card card:buildings){
					if(card.getColor() == Card.GREEN){
						greens += 1;						
					}
				}
				return stars-greens;
			}
		});		
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
	
	public static Card retrieveCard(String cardName) {
		for (Card card : cardList) {
			if (card.getName().equals(cardName)) {
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
