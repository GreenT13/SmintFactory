package model;

import card.model.Card;
import card.model.CardId;
import card.model.CardMapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that contains all the data used in the program.
 */
public class Model {
	
	// Our model will only contain a String and a Boolean.
	SimpleBooleanProperty isPlayer1CurrentPlayer;
	SimpleBooleanProperty isPlayer1StartPlayer;
	ObservableList<Card> board;
	ObservableList<Card> deck;
	public SimpleBooleanProperty newRound;
	public SimpleBooleanProperty supplyButton1Used;
	public SimpleBooleanProperty supplyButton2Used;
	
	
	
	boolean hasPreviousPlayerPassed;
	Player player1, player2;

	public Model() {
		// Instantiate objects + give default value.
		isPlayer1CurrentPlayer = new SimpleBooleanProperty(true);
		isPlayer1StartPlayer = new SimpleBooleanProperty(true);
		player1 = new Player();
		player2 = new Player();		
		player1.setPlayername("Rico");
		player2.setPlayername("Alain");
		hasPreviousPlayerPassed=false;
		board = FXCollections.observableArrayList();
		deck = FXCollections.observableArrayList();
		newRound = new SimpleBooleanProperty(false);
		supplyButton1Used = new SimpleBooleanProperty(false);
		supplyButton2Used = new SimpleBooleanProperty(false);

		
		createAndShuffleDeck();
		fillBoard();
	}
	
	public boolean moneyCheck(int playerMoney, int objectMoney){
		if (playerMoney >= objectMoney){
			return true;
		} else{
			return false;
		}
	}
	
	public void clickCard(CardId cardId, boolean isInHand, boolean isBuilt, boolean moneyCheck) {
			if (isBuilt){
				// Is gebouwd, doet niks
			} 
			else if(isInHand) {
				if(getCurrentPlayer().playerCanBuild & moneyCheck){
					// van hand -> gebouwd
					// System.out.println("Bouw mij!");
					if(getCurrentPlayer().getHand().contains(CardMapper.createCard(cardId))){
						getCurrentPlayer().hand.remove(CardMapper.createCard(cardId));
						getCurrentPlayer().buildings.add(CardMapper.createCard(cardId));
						getCurrentPlayer().playerCanBuy=false;
						getCurrentPlayer().money.setValue(getCurrentPlayer().money.getValue()-2);
						changeTurn();
					} else {
						//
					}
				} else if (getCurrentPlayer().playerCanBuild){
					System.out.println("Not enough money");
				} else if (!moneyCheck(getCurrentPlayer().money.getValue(), 2)){
					System.out.println("Press button first");
				}
			} else {
				// van bord -> hand
				if(moneyCheck == true){
					board.remove(CardMapper.createCard(cardId));
					getCurrentPlayer().hand.add(CardMapper.createCard(cardId));
					getCurrentPlayer().playerCanBuy=false;
					getCurrentPlayer().money.setValue((getCurrentPlayer().money.getValue() - CardMapper.createCard(cardId).getCost()));
					changeTurn();
					supplier(false);
				} else if (getCurrentPlayer().playerCanBuy){
					System.out.println("Not enough money");
				} else {
					System.out.println("Buy button first!");
				}
				
			}
	}
	
	public Player getPlayer(boolean isFirstPlayer){
		if(isFirstPlayer){
			return player1;
		} else{
			return player2;
		}
	}
	
	public Player getCurrentPlayer(){
		return getPlayer(isPlayer1CurrentPlayer.getValue());
	}
	
	public void changeTurn(){
		if(!player1.hasPassed & !player2.hasPassed){
			isPlayer1CurrentPlayer.setValue(!isPlayer1CurrentPlayer.getValue());
		}
		
	}
	
	public void pass(){	
		
		
		if(!player1.hasPassed & !player2.hasPassed){
			getCurrentPlayer().hasPassed = true;
			System.out.println(getCurrentPlayer().getPlayername() + getCurrentPlayer().hasPassed);
			isPlayer1CurrentPlayer.setValue(!isPlayer1CurrentPlayer.getValue());
		} else {
			getCurrentPlayer().hasPassed = true;
			System.out.println(getCurrentPlayer().getPlayername() + getCurrentPlayer().hasPassed);
		}
		

		if(player1.hasPassed & player2.hasPassed){
			endRound();
		}
	}
	
	void endRound(){
		newRound(true);
		newRound(false);
		fillBoard();
		newRound.setValue(true);
	}
	
	void newRound(boolean isPlayer1){
		isPlayer1CurrentPlayer.set(isPlayer1);
		getCurrentPlayer().money.setValue((getCurrentPlayer().getMoney().getValue() + getCurrentPlayer().getIncome().getValue()));
		// income
		// points
		getCurrentPlayer().hasPassed = false;
		isPlayer1CurrentPlayer.setValue(isPlayer1StartPlayer.getValue());
	}
	

	void createAndShuffleDeck(){
		for(Card card:CardMapper.cardProperties){
			deck.add(card);
		}
		
		// Randomize deck here.
	}
	
	public void fillBoard(){
		int size = board.size();
		for(int i = 0; i + size < 3; i++){
			board.add(deck.get(0));
			deck.remove(0);
		}
	}

	
	/* Functions from cards in the middle. */
	
	public void produce(){
		getCurrentPlayer().money.setValue(getCurrentPlayer().money.getValue()+1);
		changeTurn();
	}
	
	public void starter(){
		isPlayer1StartPlayer.set(isPlayer1CurrentPlayer.getValue());
		changeTurn();
	}
	
	public void builder(boolean buttonUsed){
		getCurrentPlayer().playerCanBuild=buttonUsed;
		//changeTurn();
	}
	
	public void supplier(boolean buttonUsed){
		getCurrentPlayer().playerCanBuy=buttonUsed;
		//changeTurn();
	}
	
	/* All getters and setters */
	
	public ObservableList<Card> getBoard() {
		return board;
	}

	public void setBoard(ObservableList<Card> board) {
		this.board = board;
	}

	public SimpleBooleanProperty getIsPlayer1CurrentPlayer() {
		return isPlayer1CurrentPlayer;
	}

	public void setIsPlayer1CurrentPlayer(SimpleBooleanProperty isPlayer1CurrentPlayer) {
		this.isPlayer1CurrentPlayer = isPlayer1CurrentPlayer;
	}

	public SimpleBooleanProperty getIsPlayer1StartPlayer() {
		return isPlayer1StartPlayer;
	}

	public void setIsPlayer1StartPlayer(SimpleBooleanProperty isPlayer1StartPlayer) {
		this.isPlayer1StartPlayer = isPlayer1StartPlayer;
	}

	public SimpleBooleanProperty getNewRound() {
		return newRound;
	}

	public void setNewRound(SimpleBooleanProperty newRound) {
		this.newRound = newRound;
	}

	public SimpleBooleanProperty getSupplyButton1Used() {
		return supplyButton1Used;
	}

	public void setSupplyButton1Used(SimpleBooleanProperty supplyButton1Used) {
		this.supplyButton1Used = supplyButton1Used;
	}

	public SimpleBooleanProperty getSupplyButton2Used() {
		return supplyButton2Used;
	}

	public void setSupplyButton2Used(SimpleBooleanProperty supplyButton2Used) {
		this.supplyButton2Used = supplyButton2Used;
	}
	
	
	
}
