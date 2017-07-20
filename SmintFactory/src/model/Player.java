package model;

import card.model.Card;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Player {
	String playername;
	SimpleIntegerProperty money;
	SimpleIntegerProperty income;
	SimpleIntegerProperty points;
	ObservableList<Card> hand;
	ObservableList<Card> buildings;
	boolean playerCanBuy, playerCanBuild;
	public boolean hasPassed;
	
	public Player(){
		money = new SimpleIntegerProperty(3);
		income = new SimpleIntegerProperty(1);
		points = new SimpleIntegerProperty(0);
		hand = FXCollections.observableArrayList();
		buildings = FXCollections.observableArrayList();
		playerCanBuy = false;
		playerCanBuild = false;
		hasPassed = false;
	}
	
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public SimpleIntegerProperty getMoney() {
		return money;
	}
	public void setMoney(SimpleIntegerProperty money) {
		this.money = money;
	}
	public SimpleIntegerProperty getIncome() {
		return income;
	}
	public void setIncome(SimpleIntegerProperty income) {
		this.income = income;
	}
	public SimpleIntegerProperty getPoints() {
		return points;
	}
	public void setPoints(SimpleIntegerProperty points) {
		this.points = points;
	}

	public ObservableList<Card> getHand() {
		return hand;
	}

	public void setHand(ObservableList<Card> hand) {
		this.hand = hand;
	}

	public ObservableList<Card> getBuildings() {
		return buildings;
	}

	public void setBuildings(ObservableList<Card> buildings) {
		this.buildings = buildings;
	}	
	
}
