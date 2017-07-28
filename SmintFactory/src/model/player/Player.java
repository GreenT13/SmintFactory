package model.player;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.card.Card;

public class Player {
	String playername;
	SimpleIntegerProperty money;
	SimpleIntegerProperty income;
	SimpleIntegerProperty points;
	SimpleIntegerProperty buildCost;
	//SimpleIntegerProperty supplyCost;
	ObservableList<Card> hand;
	ObservableList<Card> buildings;
	
	public Player(){
		money = new SimpleIntegerProperty(10);
		income = new SimpleIntegerProperty(1);
		points = new SimpleIntegerProperty(0);
		buildCost = new SimpleIntegerProperty (2);
		//supplyCost = new SimpleIntegerProperty (-1);
		hand = FXCollections.observableArrayList();
		buildings = FXCollections.observableArrayList();		
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

	public SimpleIntegerProperty getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(SimpleIntegerProperty buildCost) {
		this.buildCost = buildCost;
	}	
	
}
