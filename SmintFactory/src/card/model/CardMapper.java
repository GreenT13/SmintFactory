package card.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that will create card objects element from Deck only.
 * Heavily relies on the fact that every card is unique, having only one instance of each cardId.
 */
public class CardMapper {
	
	public static List<Card> cardProperties;
	
	static {
		cardProperties = new ArrayList<Card>();
		// Define all cards with properties here.
		cardProperties.add(new Card("Simpel rood", 1, 0, 1, Type.RED, CardId.SIMPLE_RED));
		cardProperties.add(new Card("Simpel groen", 0, 1, 1, Type.GREEN, CardId.SIMPLE_GREEN));
		cardProperties.add(new Card("Simpel geel", 1, 1, 1, Type.YELLOW, CardId.SIMPLE_YELLOW));
		cardProperties.add(new Card("Speciaal2", 2, 2, 2, Type.RED, CardId.SPECIAL));
		cardProperties.add(new Card("Simpel rood2", 1, 0, 1, Type.RED, CardId.SIMPLE_RED));
		cardProperties.add(new Card("Simpel groen2", 0, 1, 1, Type.GREEN, CardId.SIMPLE_GREEN));
		cardProperties.add(new Card("Simpel geel2", 1, 1, 1, Type.YELLOW, CardId.SIMPLE_YELLOW));
		cardProperties.add(new Card("Speciaal2", 2, 2, 2, Type.RED, CardId.SPECIAL));
		cardProperties.add(new Card("Simpel rood3", 1, 0, 1, Type.RED, CardId.SIMPLE_RED));
		cardProperties.add(new Card("Simpel groen3", 0, 1, 1, Type.GREEN, CardId.SIMPLE_GREEN));
		cardProperties.add(new Card("Simpel geel3", 1, 1, 1, Type.YELLOW, CardId.SIMPLE_YELLOW));
		cardProperties.add(new Card("Speciaal3", 2, 2, 2, Type.RED, CardId.SPECIAL));
	}

	/**
	 * Return (singleton) instance of the used card specified by cardId.
	 * @param cardId
	 * @return Instance of Card.
	 */
	public static Card createCard(CardId cardId) {
		// Search the card with the same cardId.
		for (Card card : cardProperties) {
			if (card.getCardId().equals(cardId)) {
				return card;
			}
		}
		return null;
	}
}
