package model;

import card.model.Card;
import card.model.CardId;
import card.model.CardMapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * Class that contains all the data used in the program.
 */
public class Model {

	@FXML VBox leftActionBox, rightActionBox;
	
	// Our model will only contain a String and a Boolean.
	SimpleBooleanProperty isPlayer1CurrentPlayer;
	SimpleBooleanProperty isPlayer1StartPlayer;
	ObservableList<Card> board;
	ObservableList<Card> deck;
	public SimpleBooleanProperty newRound;
	public SimpleBooleanProperty supplyButtonPressed;
	public SimpleBooleanProperty buildButtonPressed;
	public SimpleBooleanProperty recycleButtonPressed;
	public SimpleBooleanProperty sevenPointsReached;
	public SimpleStringProperty gameConsoleText;
	public SimpleBooleanProperty lottoBought;
	public SimpleBooleanProperty wholesalerBought;
	
	boolean hasPreviousPlayerPassed;
	Player player1, player2;
	Player nobody;

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
		supplyButtonPressed = new SimpleBooleanProperty(false);
		buildButtonPressed = new SimpleBooleanProperty(false);
		recycleButtonPressed = new SimpleBooleanProperty(false);
		sevenPointsReached = new SimpleBooleanProperty(false);
		lottoBought = new SimpleBooleanProperty(false);
		wholesalerBought = new SimpleBooleanProperty(false);
		
		nobody = new Player();
		nobody.setPlayername("Nobody");
		gameConsoleText = new SimpleStringProperty("Game starts");

		
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
	
	public void addGameConsoleText(String text){
		gameConsoleText.setValue(text + System.lineSeparator() + gameConsoleText.getValue());
	}
	
	public void recycleCard(CardId cardId){
		if(getCurrentPlayer().getHand().contains(CardMapper.createCard(cardId))){
			getCurrentPlayer().getHand().remove(CardMapper.createCard(cardId));
			getCurrentPlayer().money.set(getCurrentPlayer().getMoney().getValue() + CardMapper.createCard(cardId).getStars() + CardMapper.createCard(cardId).getCost());
			changeTurn();
		} else if(getCurrentPlayer().getBuildings().contains(CardMapper.createCard(cardId))) {
			getCurrentPlayer().getBuildings().remove(CardMapper.createCard(cardId));
			getCurrentPlayer().money.set(getCurrentPlayer().getMoney().getValue() + CardMapper.createCard(cardId).getStars() + CardMapper.createCard(cardId).getCost());
			changeTurn();
		} else {
			addGameConsoleText("Pick a plan from your hand");
		}
	}
	
	public void buildCard(CardId cardId){
		if(getCurrentPlayer().getHand().contains(CardMapper.createCard(cardId))){
			getCurrentPlayer().getHand().remove(CardMapper.createCard(cardId));
			getCurrentPlayer().buildings.add(CardMapper.createCard(cardId));
			getCurrentPlayer().money.setValue((getCurrentPlayer().money.getValue() - 2));
			buildButtonPressed.setValue(false);
			changeTurn();
		} else {
			addGameConsoleText("That card is not in your hand");
		}
	}
	
	public void buyCard(CardId cardId){
		if(board.contains(CardMapper.createCard(cardId))){
			board.remove(CardMapper.createCard(cardId));
			getCurrentPlayer().hand.add(CardMapper.createCard(cardId));
			getCurrentPlayer().money.setValue((getCurrentPlayer().money.getValue() - CardMapper.createCard(cardId).getCost()));
			supplyButtonPressed.setValue(false);
			changeTurn();
		} else {
			addGameConsoleText("That card is not on the board");
		}
	}
		
	boolean checkActionCards(Button button){
		if(getPlayer(true).getBuildings().contains(button)){
			 return true;
		 } else if(getPlayer(false).getBuildings().contains(button)){
			 return true;
		 } else {
			 return false;
		 }
	}
	
	void checkLottoBought(){
		if(getPlayer(true).getBuildings().contains(CardMapper.createCard(CardId.LOTTO))){
			lottoBought.set(true);
		}
		if(getPlayer(false).getBuildings().contains(CardMapper.createCard(CardId.LOTTO))){
			lottoBought.set(true);
		}
	}
	
	void checkWholesalerBought(){
		if(getPlayer(true).getBuildings().contains(CardMapper.createCard(CardId.WHOLESALER))){
			wholesalerBought.set(true);
		}
		if(getPlayer(false).getBuildings().contains(CardMapper.createCard(CardId.WHOLESALER))){
			wholesalerBought.set(true);
		}
	}
	

	
	
	
	void updatePlayerProperties(boolean isPlayer1){
		int totalIncome=1, totalPoints=0;
		for(Card card:getPlayer(isPlayer1).getBuildings()){
			totalIncome = totalIncome + card.getExtraMoney();
			totalPoints = totalPoints + card.getStars();
		}
		getPlayer(isPlayer1).getIncome().set(totalIncome);
		getPlayer(isPlayer1).getPoints().set(totalPoints);
		getPlayer(isPlayer1).getMoney().setValue(getPlayer(isPlayer1).getMoney().getValue()+getPlayer(isPlayer1).getIncome().getValue());
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
	
	public Player getOtherPlayer(){
		return getPlayer(!isPlayer1CurrentPlayer.getValue());
	}
	
	public void changeTurn(){
		if(!player1.hasPassed & !player2.hasPassed){
			isPlayer1CurrentPlayer.setValue(!isPlayer1CurrentPlayer.getValue());
		}
	}
	
	public void pass(){	
		
		
		if(!player1.hasPassed & !player2.hasPassed){
			getCurrentPlayer().hasPassed = true;
			addGameConsoleText(getCurrentPlayer().getPlayername() + " has passed.");
			isPlayer1CurrentPlayer.setValue(!isPlayer1CurrentPlayer.getValue());
		} else {
			getCurrentPlayer().hasPassed = true;
			System.out.println(getCurrentPlayer().getPlayername() + " has passed.");
		}
		

		if(player1.hasPassed & player2.hasPassed){
			endRound();
		}
	}
	
	void endRound(){
		getSevenPointsReached();
		if(sevenPointsReached.getValue()==true){
			determineWinner();
		} else{
			addGameConsoleText("Both players have passed. New round!");
			newRound(true);
			newRound(false);
			fillBoard();
			newRound.setValue(true);
		}
	}
	
	void newRound(boolean isPlayer1){
		isPlayer1CurrentPlayer.set(isPlayer1);
		updatePlayerProperties(isPlayer1);
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
	
	public void getSevenPointsReached(){
		if(player1.getPoints().getValue()>=7 | player2.getPoints().getValue()>=7){
			sevenPointsReached.setValue(true);
		} else {
			sevenPointsReached.setValue(false);
		}
	}
	
	public Player determineWinner(){
		if (sevenPointsReached.getValue() == true){
			if(player1.getPoints().getValue()>player2.getPoints().getValue()){
				System.out.println("Player 1 wins the game!");
				return player1;
			} else if (player2.getPoints().getValue()>player1.getPoints().getValue()){
				System.out.println("Player 2 wins the game!");
				return player2;
			} else {
				if(player1.getMoney().getValue()>player2.getMoney().getValue()){
					System.out.println("Player 1 wins the game!");
					return player1;
				} else if (player2.getMoney().getValue()>player1.getMoney().getValue()){
					System.out.println("Player 2 wins the game!");
					return player2;
				} else{
					System.out.println("It's a tie!");
					return nobody;
				}
			}
		} else{
			return nobody;
		}
		
	}
	
	/* Functions from cards in the middle. */
	
	public void produce(){
		getCurrentPlayer().money.setValue(getCurrentPlayer().money.getValue()+1);
		changeTurn();
	}
	
	public void starter(){
		isPlayer1StartPlayer.set(isPlayer1CurrentPlayer.getValue());
		addGameConsoleText(getPlayer(isPlayer1CurrentPlayer.getValue()).getPlayername() + " is now the Starting Player.");
		changeTurn();
	}
	
	public void builder(boolean buttonUsed){
		buildButtonPressed.set(buttonUsed);
	}
	
	public void supplier(boolean buttonUsed){
		supplyButtonPressed.set(buttonUsed);
	}
	
	public void crowdfund(){
		getCurrentPlayer().money.setValue(getCurrentPlayer().money.getValue()+2);
		getOtherPlayer().money.setValue(getOtherPlayer().money.getValue()+1);
		changeTurn();
	}
	
	public void recycler(boolean buttonUsed){
		recycleButtonPressed.set(buttonUsed);
	}
	
	public boolean checkButton(Button button){
		if(leftActionBox.getChildren().contains(button)){
			return true;
		} else if (rightActionBox.getChildren().contains(button)){
			return true;
		} else {
			return false;
		}
	}
	
	public void newRoundListener(Button button){
		getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
					button.setDisable(false);
					
					newRound.setValue(false);	
			}
		});
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

	public SimpleBooleanProperty getSupplyButtonPressed() {
		return supplyButtonPressed;
	}

	public void setSupplyButtonPressed(SimpleBooleanProperty supplyButtonPressed) {
		this.supplyButtonPressed = supplyButtonPressed;
	}

	public SimpleBooleanProperty getBuildButtonPressed() {
		return buildButtonPressed;
	}

	public void setBuildButtonPressed(SimpleBooleanProperty buildButtonPressed) {
		this.buildButtonPressed = buildButtonPressed;
	}

	

	
}
