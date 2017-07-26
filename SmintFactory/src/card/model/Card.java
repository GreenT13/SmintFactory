package card.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Card {
	
	
	
	String name;
	Integer extraMoney;
	Integer stars;
	Integer cost;
	String type;
	CardId cardId;
	
	public Card() {
		// 
	}
	
	public Card(String name, Integer extraMoney, Integer stars, Integer cost, String type, CardId cardId) {
		this.name = name;
		this.extraMoney = extraMoney;
		this.stars = stars;
		this.cost = cost;
		this.type = type;
		this.cardId = cardId;
	}


	//Methods that can be overridden to create special cards.
	public Integer calculateStars() {
		return stars;
	}
	
	public Integer calculateExtraMoney() {
		return extraMoney;
	}
	
	
	// Getters and setters.
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getExtraMoney() {
		return extraMoney;
	}
	public void setExtraMoney(Integer extraMoney) {
		this.extraMoney = extraMoney;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public CardId getCardId() {
		return cardId;
	}
	public void setCardId(CardId cardId) {
		this.cardId = cardId;
	}

}
