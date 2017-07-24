package newcard;

public class Card {
	
	public final static String RED = "red";
	public final static String YELLOW = "yellow";
	public final static String GREEN = "green";

	String name;
	Integer extraMoney;
	Integer stars;
	Integer cost;
	String color;
	Integer cardId;
	
	public Card() {
		// 
	}
	
	public Card(String name, Integer extraMoney, Integer stars, Integer cost, String color) {
		this.name = name;
		this.extraMoney = extraMoney;
		this.stars = stars;
		this.cost = cost;
		this.color = color;
	}
	
	// Methods that can be overridden to create special cards.
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
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
}
