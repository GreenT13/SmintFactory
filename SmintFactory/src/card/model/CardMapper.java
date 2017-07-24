package card.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that will create card objects element from Deck only.
 * Heavily relies on the fact that every card is unique, having only one instance of each cardId.
 */
public class CardMapper {
	
	public static List<Card> cardProperties;
	
	//this.name = name;
//	this.extraMoney = extraMoney;
//	this.stars = stars;
//	this.cost = cost;
//	this.type = type;
//	this.cardId = cardId;
//	
	static {
		cardProperties = new ArrayList<Card>();
		// Define all cards with properties here.
		cardProperties.add(new Card("Corporate HQ", 1, 0, 3, "red", CardId.CORPORATE_HQ));
		cardProperties.add(new Card("Wholesaler", 0, 1, 1, "aquamarine", CardId.WHOLESALER));
		cardProperties.add(new Card("Lotto", 0, 2, 4, "aquamarine", CardId.LOTTO));
		cardProperties.add(new Card("Mine", 1, 1, 2, "red", CardId.MINE));
		cardProperties.add(new Card("Assembler", 0, 1, 5, "yellow", CardId.ASSEMBLER));
		cardProperties.add(new Card("Workshop", 1, 2, 3, "red", CardId.WORKSHOP));
		cardProperties.add(new Card("Windmill", 0, 1, 1, "green", CardId.WINDMILL));
		cardProperties.add(new Card("Corporate HQ", 1, 0, 3, "red", CardId.CORPORATE_HQ));
		cardProperties.add(new Card("Bridge", 0, 0, 1, "green", CardId.BRIDGE));
		cardProperties.add(new Card("Factory", 1, 3, 4, "red", CardId.FACTORY));
		cardProperties.add(new Card("Co-op", 1, 1, 1, "red", CardId.COOP));
		cardProperties.add(new Card("Plant", 2, 2, 5, "red", CardId.PLANT));
		cardProperties.add(new Card("Gallery", 0, 1, 4, "green", CardId.GALLERY));
		cardProperties.add(new Card("Gardens", 0, 3, 3, "green", CardId.GARDENS));
		cardProperties.add(new Card("Statue", 0, 2, 2, "green", CardId.STATUE));
		cardProperties.add(new Card("Stripmine", 3, 0, 4, "red", CardId.STRIPMINE));
		cardProperties.add(new Card("Vault", 0, 2, 5, "yellow", CardId.VAULT));
		cardProperties.add(new Card("Obelisk", 0, 1, 4, "yellow", CardId.OBELISK));
		cardProperties.add(new Card("Museum", 0, 1, 2, "green", CardId.MUSEUM));
		cardProperties.add(new Card("Truck", 0, 1, 2, "yellow", CardId.TRUCK));
		cardProperties.add(new Card("Crane", 0, 1, 2, "yellow", CardId.CRANE));
		cardProperties.add(new Card("Landfill", 0, 3, 3, "yellow", CardId.LANDFILL));		
		
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
