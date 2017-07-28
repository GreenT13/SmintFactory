package model;

import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.card.Card;
import model.card.CardManager;
import model.player.Player;


/**
 * Class that contains all the data used in the program.
 */
public class Model implements IModel {
	
	// Our model will only contain a String and a Boolean.
	Player player1, player2;
	ObservableList<Card> board;
	ObservableList<Card> deck;
	SimpleIntegerProperty currentPlayerBuildCost;
	SimpleStringProperty gameConsoleText;
	// Actieknoppen
	SimpleBooleanProperty isProducerButton1Used;
	SimpleBooleanProperty isProducerButton2Used;
	SimpleBooleanProperty isSupplierButton1Used;
	SimpleBooleanProperty isSupplierButton2Used;
	SimpleBooleanProperty isBuilderButton1Used;
	SimpleBooleanProperty isBuilderButton2Used;
	SimpleBooleanProperty isStarterButtonUsed;
	SimpleBooleanProperty isSwapButtonUsed;
	SimpleBooleanProperty isTempButtonUsed;
	SimpleBooleanProperty isLottoButtonUsed;
	SimpleBooleanProperty isWholesalerButtonUsed;
	SimpleBooleanProperty isRecyclerButtonUsed;
	SimpleBooleanProperty isCrowdfunderButtonUsed;
	
	SimpleBooleanProperty isPlayer1CurrentPlayer;
	SimpleBooleanProperty hasPlayer1StartingToken;
	boolean hasPreviousPlayerPassed;

	SimpleIntegerProperty newRound;
	SimpleBooleanProperty lottoBought;
	SimpleBooleanProperty wholesalerBought;

	State currentState;
	
	//Cumswap
	Integer swapBoardCardId, swapHandOrBuiltCardId;
	

	
	
	public Model() {
		// Instantiate objects + give default value.
		player1 = new Player();
		player1.setPlayername("Rico");
		player2 = new Player();	
		player2.setPlayername("Alain");
		board = FXCollections.observableArrayList();
		deck = FXCollections.observableArrayList();
		currentPlayerBuildCost = new SimpleIntegerProperty(2);
		gameConsoleText = new SimpleStringProperty("Game starts");
		
		isProducerButton1Used = new SimpleBooleanProperty(false);
		isProducerButton2Used = new SimpleBooleanProperty(false);
		isSupplierButton1Used = new SimpleBooleanProperty(false);
		isSupplierButton2Used = new SimpleBooleanProperty(false);
		isBuilderButton1Used = new SimpleBooleanProperty(false);
		isBuilderButton2Used = new SimpleBooleanProperty(false);
		isStarterButtonUsed = new SimpleBooleanProperty(false);
		isSwapButtonUsed = new SimpleBooleanProperty(false);
		isTempButtonUsed = new SimpleBooleanProperty(false);
		isLottoButtonUsed = new SimpleBooleanProperty(false);
		isWholesalerButtonUsed = new SimpleBooleanProperty(false);
		isRecyclerButtonUsed = new SimpleBooleanProperty(false);
		isCrowdfunderButtonUsed = new SimpleBooleanProperty(false);
		
		isPlayer1CurrentPlayer = new SimpleBooleanProperty(true);
		hasPlayer1StartingToken = new SimpleBooleanProperty(true);
		hasPreviousPlayerPassed=false;
		
		newRound = new SimpleIntegerProperty(0);
		lottoBought = new SimpleBooleanProperty(false);
		wholesalerBought = new SimpleBooleanProperty(false);
		
		getPlayer(true).getBuildings().addListener(new ListChangeListener<Card>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				if(getPlayer(true).getBuildings().contains(CardManager.retrieveCard("Crane"))){
					getPlayer(true).getBuildCost().set(1);
					System.out.print("ASDAS");
				} else {
					getPlayer(true).getBuildCost().set(2);
				}
				System.out.print("ASDAS");
			}
		});
		getPlayer(false).getBuildings().addListener(new ListChangeListener<Card>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				if(getPlayer(false).getBuildings().contains(CardManager.retrieveCard("Crane"))){
					getPlayer(false).getBuildCost().set(1);
					System.out.print("ASDAS");
				} else {
					getPlayer(false).getBuildCost().set(2);
				}
			}
		});
		
		createAndShuffleDeck();
		fillBoard();
	}
	
	void createAndShuffleDeck(){
		for(Card card: CardManager.cardList){
			deck.add(card);
		}
		
		// Randomize deck here.
	}
	
	void fillBoard(){
		int size = board.size();
		for(int i = 0; i + size < 3; i++){
			board.add(drawCardFromDeck());
		}
	}

	
	public void clickCard(Integer cardId, boolean inPlans, boolean isBuilt){
		if(currentState == null){
			addGameConsoleText("Can't click card");
			return;
		} 
		
		switch(currentState.getAction()){
		case SUPPLY:
			if(!inPlans && !isBuilt){
				buyCard(cardId);
			} else{
				addGameConsoleText("That card is not in your hand");
				return;
			}
			break;
		case BUILD:
			if(inPlans){
				buildCard(cardId);
			} else{
				addGameConsoleText("Cannot build that card");
				return;
			}
			break;
		case SWAP:
			if(!inPlans && !isBuilt){
				swapBoardCard(cardId);
			} else if (inPlans || isBuilt){
				swapHandOrBuiltCard(cardId);
			} else{
				return;
			}
			break;
		case RECYCLE:
			if (inPlans || isBuilt) {
				recycleCard(cardId, inPlans);
			}
			break;
		default: 
			addGameConsoleText("ERROR");
			return;
		}
	}
	
	private void recycleCard(Integer cardId, boolean inPlans) {
		if(searchCardFromList(getCurrentPlayer().getHand(), cardId) != null){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue()+CardManager.retrieveCard(cardId).getStars() + CardManager.retrieveCard(cardId).getCost());
			if(inPlans){
				getCurrentPlayer().getHand().remove(CardManager.retrieveCard(cardId));
			} else {
				getCurrentPlayer().getBuildings().remove(CardManager.retrieveCard(cardId));
			}
			isRecyclerButtonUsed.setValue(true);
			currentState = null;
			changeTurn();
		} else {
			addGameConsoleText("That is not your card");
		}
	}

	void swapBoardCard(Integer cardId){
		swapBoardCardId = cardId;
		
		if(swapHandOrBuiltCardId != null){
			addGameConsoleText(getCurrentPlayer().getPlayername() + " swapped " + CardManager.retrieveCard(swapHandOrBuiltCardId).getName() + " for " + CardManager.retrieveCard(swapBoardCardId).getName());
			swapCards();
		} else {
			addGameConsoleText("You will get " + CardManager.retrieveCard(cardId).getName());
		}
	}
	
	void swapHandOrBuiltCard(Integer cardId){
		swapHandOrBuiltCardId = cardId;
		
		if(swapBoardCardId != null){
			addGameConsoleText(getCurrentPlayer().getPlayername() + " swapped " + CardManager.retrieveCard(swapHandOrBuiltCardId).getName() + " for " + CardManager.retrieveCard(swapBoardCardId).getName());
			swapCards();
		} else {
			addGameConsoleText("You will lose " + CardManager.retrieveCard(cardId).getName());
		}
	}
	
	void resetButtons(){
		isProducerButton1Used.setValue(false);
		isProducerButton2Used.setValue(false);
		isSupplierButton1Used.setValue(false);
		isSupplierButton2Used.setValue(false);
		isBuilderButton1Used.setValue(false);
		isBuilderButton2Used.setValue(false);
		isStarterButtonUsed.setValue(false);
		isSwapButtonUsed.setValue(false);
		isTempButtonUsed.setValue(false);
		isLottoButtonUsed.setValue(false);
		isWholesalerButtonUsed.setValue(false);
		isRecyclerButtonUsed.setValue(false);
		isCrowdfunderButtonUsed.setValue(false);
	}
	
	void swapCards(){
		if(searchCardFromList(getCurrentPlayer().getHand(), swapHandOrBuiltCardId) != null){
			board.add(drawCardFromList(getCurrentPlayer().getHand(), swapHandOrBuiltCardId));
			getCurrentPlayer().getHand().add(drawCardFromList(board, swapBoardCardId));
		} else {
			board.add(drawCardFromList(getCurrentPlayer().getBuildings(), swapHandOrBuiltCardId));
			getCurrentPlayer().getBuildings().add(drawCardFromList(board, swapBoardCardId));
		}
		currentState = null;
		isSwapButtonUsed.setValue(true);
		swapHandOrBuiltCardId = null;
		swapBoardCardId = null;
		changeTurn();		
	}
	
	
	void buyCard(Integer cardId){
		if(getCurrentPlayer().getMoney().getValue() >= searchCardFromList(board, cardId).getCost()){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue() - searchCardFromList(board, cardId).getCost());
			if(getCurrentPlayer().getBuildings().contains(CardManager.retrieveCard("Assembler"))){
				getCurrentPlayer().getBuildings().add(drawCardFromList(board, cardId));
			} else {
				getCurrentPlayer().getHand().add(drawCardFromList(board, cardId));
			}
			
			if(currentState.isButton1Used()){
				isSupplierButton1Used.setValue(true);
			} else {
				isSupplierButton2Used.setValue(true);
			}
			currentState = null;
			changeTurn();
		} else {
			addGameConsoleText("Not enough money");
		}
	}
	
	void buildCard(Integer cardId){
		if(searchCardFromList(getCurrentPlayer().getHand(), cardId) == null){
			addGameConsoleText("That card is not in your plans");
			return;
		}
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue() - currentPlayerBuildCost.getValue());
			getCurrentPlayer().getBuildings().add(drawCardFromList(getCurrentPlayer().getHand(), cardId));
			if(currentState.isButton1Used()){
				isBuilderButton1Used.setValue(true);
			} else {
				isBuilderButton2Used.setValue(true);
			}
			currentState = null;
			changeTurn();	
	}
	
	Card drawCardFromList(List<Card> list, Integer cardId){
		Card card = searchCardFromList(list, cardId);
		list.remove(card);
		return card;
	}
	
	Card searchCardFromList(List<Card> list, Integer cardId){
		for (Card card:list){
			if(card.getCardId().equals(cardId)){
				return card;
			}
		}
		System.out.print("searchCardFromList ERROR");
		//addGameConsoleText("ERROR did not find card");
		return null;
	}
	
	public void doAction(Action action, boolean usedButton1){
		currentState = null;
		
		switch(action){
		case PASS: 
			doPass();
			break; 
		case PRODUCE: 
			doProduce(usedButton1);
			changeTurn();
			break;
		case BUILD: doBuild(usedButton1);
			break;
		case SUPPLY: doSupply(usedButton1);
			break;
		case START: 
			doStart();
			changeTurn();
			break;
		case WHOLESALER: 
			doWholesale();
			changeTurn();
			break;
		case LOTTO: 
			doLotto();
			changeTurn();
			break;
		case SWAP: doSwap(usedButton1);
			break;
		case TEMP: doTemp();
			break;
		case RECYCLE: doRecycle(usedButton1);
			break;
		case CROWDFUND: 
			doCrowdfund();
			changeTurn();
			break;
		}
		if(action != Action.PASS){
			hasPreviousPlayerPassed = false;
		}
		
	}
	
	private void doPass() {
		if(!hasPreviousPlayerPassed){
			hasPreviousPlayerPassed=true;
			changeTurn();
		} else {
			createNewRound();
		}
	}
	
	void createNewRound(){
		if(player1.getPoints().getValue()>=7 | player2.getPoints().getValue()>=7){
			determineWinner();
		} else{
			addGameConsoleText("Both players have passed. New round!");
			isPlayer1CurrentPlayer.setValue(hasPlayer1StartingToken.getValue());
			updatePlayerProperties(true);
			updatePlayerProperties(false);
			fillBoard();
			resetButtons();
			newRound.setValue(newRound.getValue()+1);
		}
	}
		
	void updatePlayerProperties(boolean isPlayer1){
		int totalIncome=1, totalPoints=0;
		for(Card card:getPlayer(isPlayer1).getBuildings()){
			totalIncome = totalIncome + card.determineExtraMoney(getPlayer(isPlayer1).getHand(), getPlayer(isPlayer1).getBuildings());
			totalPoints = totalPoints + card.determineStars(getPlayer(isPlayer1).getHand(), getPlayer(isPlayer1).getBuildings());
		}
		getPlayer(isPlayer1).getIncome().set(totalIncome);
		getPlayer(isPlayer1).getPoints().set(totalPoints);
		getPlayer(isPlayer1).getMoney().setValue(getPlayer(isPlayer1).getMoney().getValue()+totalIncome);
	}
	
	void determineWinner(){
		if(player1.getPoints().getValue()>player2.getPoints().getValue()){
			addGameConsoleText("Player 1 wins the game!");
		} else if (player2.getPoints().getValue()>player1.getPoints().getValue()){
			addGameConsoleText("Player 2 wins the game!");
		} else {
			if(player1.getMoney().getValue()>player2.getMoney().getValue()){
				addGameConsoleText("Player 1 wins the game!");
			} else if (player2.getMoney().getValue()>player1.getMoney().getValue()){
				addGameConsoleText("Player 2 wins the game!");
			} else{
				addGameConsoleText("It's a tie!");
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
	
	public Player getOtherPlayer() {
		return getPlayer(!isPlayer1CurrentPlayer.getValue());
	}
	
	void addGameConsoleText(String text){
		gameConsoleText.setValue(text + System.lineSeparator() + gameConsoleText.getValue());
	}
	
	void changeTurn(){
		addGameConsoleText((getCurrentPlayer().getPlayername() + " buildcost is " + getCurrentPlayer().getBuildCost().getValue()));
		
		isPlayer1CurrentPlayer.setValue(!isPlayer1CurrentPlayer.getValue());
		currentPlayerBuildCost.setValue(getCurrentPlayer().getBuildCost().getValue());
	}
	
	private void doProduce(boolean isButton1) {
		if(getCurrentPlayer().getMoney().getValue() >= 1){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue()+1);
			if(isButton1){
				isProducerButton1Used.setValue(true);
			} else {
				isProducerButton2Used.setValue(true);
			}
		} else{
			addGameConsoleText("No money for produce");
		}
	}

	private void doBuild(boolean isButton1) {
		if(getCurrentPlayer().getHand().size()>0){
			if(getCurrentPlayer().getMoney().getValue() >= getCurrentPlayerBuildCost().getValue()){
				currentState = new State(Action.BUILD, isButton1);
				System.out.print(getCurrentState().getAction());
			} else {
				addGameConsoleText("No money for building");
			}
		} else {
			addGameConsoleText("You ain't got no plans, son");
		}
			
	}
	
	

	private void doSupply(boolean isButton1) {
		currentState = new State(Action.SUPPLY, isButton1);
	}

	private void doStart() {
		if(getCurrentPlayer().getMoney().getValue() >= 1){
			hasPlayer1StartingToken.set(isPlayer1CurrentPlayer.getValue());
			addGameConsoleText(getPlayer(isPlayer1CurrentPlayer.getValue()).getPlayername() + " is now the Starting Player.");
			isStarterButtonUsed.setValue(true);
		} else {
			addGameConsoleText("You do not have enough smints");
		}
		
	}

	private void doWholesale() {
		if(getCurrentPlayer().getMoney().getValue() >= 1){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue()+1);
			isWholesalerButtonUsed.setValue(true);
		} else {
			addGameConsoleText("You do not have enough smints");
		}
	}

	private void doLotto() {
		if(getCurrentPlayer().getMoney().getValue() >= 3){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue()-3);;
			isLottoButtonUsed.setValue(true);
			getCurrentPlayer().getHand().add(drawCardFromDeck());
		} else {
			addGameConsoleText("You do not have enough smints");
		}
	}

	private void doSwap(boolean isButton1) {
		if(getCurrentPlayer().getHand().size() >= 1 || getCurrentPlayer().getBuildings().size() >= 1){
			if(getCurrentPlayer().getMoney().getValue() >= 2){
				currentState = new State(Action.SWAP, isButton1);
			} else {
				addGameConsoleText("You do not have enough smints");
			}
		} else{
			addGameConsoleText("You don't have anything to swap");
		}
		
	}

	private void doTemp() {
		// TODO Auto-generated method stub
		
	}

	private void doRecycle(boolean isButton1) {
		if(getCurrentPlayer().getMoney().getValue() >= 1){
			currentState = new State(Action.RECYCLE, isButton1);
		} else {
			addGameConsoleText("You do not have enough smints");
		}
	}

	private void doCrowdfund() {
		if(getCurrentPlayer().getMoney().getValue() >= 1){
			getCurrentPlayer().getMoney().setValue(getCurrentPlayer().getMoney().getValue()+2);
			getOtherPlayer().getMoney().setValue(getOtherPlayer().getMoney().getValue()+1);
			isCrowdfunderButtonUsed.setValue(true);
		} else{
			addGameConsoleText("No money for crowdfunding");
		}
	}

	Card drawCardFromDeck(){
		try{
			Card card = deck.get(0);
			deck.remove(0);
			return card;
		} catch(Exception e){
			determineWinner();
			return null;
		}
		
	}
	
	
	
	
	
	
	// Getters & setters
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public ObservableList<Card> getBoard() {
		return board;
	}

	public void setBoard(ObservableList<Card> board) {
		this.board = board;
	}

	public ObservableList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ObservableList<Card> deck) {
		this.deck = deck;
	}
	
	public SimpleIntegerProperty getCurrentPlayerBuildCost() {
		return currentPlayerBuildCost;
	}

	public void setCurrentPlayerBuildCost(SimpleIntegerProperty currentPlayerBuildCost) {
		this.currentPlayerBuildCost = currentPlayerBuildCost;
	}

	public SimpleStringProperty getGameConsoleText() {
		return gameConsoleText;
	}

	public void setGameConsoleText(SimpleStringProperty gameConsoleText) {
		this.gameConsoleText = gameConsoleText;
	}

	public SimpleBooleanProperty getIsProducerButton1Used() {
		return isProducerButton1Used;
	}

	public void setIsProducerButton1Used(SimpleBooleanProperty isProducerButton1Used) {
		this.isProducerButton1Used = isProducerButton1Used;
	}

	public SimpleBooleanProperty getIsProducerButton2Used() {
		return isProducerButton2Used;
	}

	public void setIsProducerButton2Used(SimpleBooleanProperty isProducerButton2Used) {
		this.isProducerButton2Used = isProducerButton2Used;
	}

	public SimpleBooleanProperty getIsSupplierButton1Used() {
		return isSupplierButton1Used;
	}

	public void setIsSupplierButton1Used(SimpleBooleanProperty isSupplierButton1Used) {
		this.isSupplierButton1Used = isSupplierButton1Used;
	}

	public SimpleBooleanProperty getIsSupplierButton2Used() {
		return isSupplierButton2Used;
	}

	public void setIsSupplierButton2Used(SimpleBooleanProperty isSupplierButton2Used) {
		this.isSupplierButton2Used = isSupplierButton2Used;
	}

	public SimpleBooleanProperty getIsBuilderButton1Used() {
		return isBuilderButton1Used;
	}

	public void setIsBuilderButton1Used(SimpleBooleanProperty isBuilderButton1Used) {
		this.isBuilderButton1Used = isBuilderButton1Used;
	}

	public SimpleBooleanProperty getIsBuilderButton2Used() {
		return isBuilderButton2Used;
	}

	public void setIsBuilderButton2Used(SimpleBooleanProperty isBuilderButton2Used) {
		this.isBuilderButton2Used = isBuilderButton2Used;
	}

	public SimpleBooleanProperty getIsStarterButtonUsed() {
		return isStarterButtonUsed;
	}

	public void setIsStarterButtonUsed(SimpleBooleanProperty isStarterButtonUsed) {
		this.isStarterButtonUsed = isStarterButtonUsed;
	}

	public SimpleBooleanProperty getIsSwapButtonUsed() {
		return isSwapButtonUsed;
	}

	public void setIsSwapButtonUsed(SimpleBooleanProperty isSwapButtonUsed) {
		this.isSwapButtonUsed = isSwapButtonUsed;
	}

	public SimpleBooleanProperty getIsTempButtonUsed() {
		return isTempButtonUsed;
	}

	public void setIsTempButtonUsed(SimpleBooleanProperty isTempButtonUsed) {
		this.isTempButtonUsed = isTempButtonUsed;
	}

	public SimpleBooleanProperty getIsLottoButtonUsed() {
		return isLottoButtonUsed;
	}

	public void setIsLottoButtonUsed(SimpleBooleanProperty isLottoButtonUsed) {
		this.isLottoButtonUsed = isLottoButtonUsed;
	}

	public SimpleBooleanProperty getIsWholesalerButtonUsed() {
		return isWholesalerButtonUsed;
	}

	public void setIsWholesalerButtonUsed(SimpleBooleanProperty isWholesalerButtonUsed) {
		this.isWholesalerButtonUsed = isWholesalerButtonUsed;
	}

	public SimpleBooleanProperty getIsRecyclerButtonUsed() {
		return isRecyclerButtonUsed;
	}

	public void setIsRecyclerButtonUsed(SimpleBooleanProperty isRecyclerButtonUsed) {
		this.isRecyclerButtonUsed = isRecyclerButtonUsed;
	}

	public SimpleBooleanProperty getIsCrowdfunderButtonUsed() {
		return isCrowdfunderButtonUsed;
	}

	public void setIsCrowdfunderButtonUsed(SimpleBooleanProperty isCrowdfunderButtonUsed) {
		this.isCrowdfunderButtonUsed = isCrowdfunderButtonUsed;
	}

	public SimpleBooleanProperty getIsPlayer1CurrentPlayer() {
		return isPlayer1CurrentPlayer;
	}

	public void setIsPlayer1CurrentPlayer(SimpleBooleanProperty isPlayer1CurrentPlayer) {
		this.isPlayer1CurrentPlayer = isPlayer1CurrentPlayer;
	}

	public SimpleBooleanProperty getIsPlayer1StartPlayer() {
		return hasPlayer1StartingToken;
	}

	public void setIsPlayer1StartPlayer(SimpleBooleanProperty isPlayer1StartPlayer) {
		this.hasPlayer1StartingToken = isPlayer1StartPlayer;
	}

	public boolean isHasPreviousPlayerPassed() {
		return hasPreviousPlayerPassed;
	}

	public void setHasPreviousPlayerPassed(boolean hasPreviousPlayerPassed) {
		this.hasPreviousPlayerPassed = hasPreviousPlayerPassed;
	}

	public SimpleIntegerProperty getNewRound() {
		return newRound;
	}

	public void setNewRound(SimpleIntegerProperty newRound) {
		this.newRound = newRound;
	}

	public SimpleBooleanProperty getLottoBought() {
		return lottoBought;
	}

	public void setLottoBought(SimpleBooleanProperty lottoBought) {
		this.lottoBought = lottoBought;
	}

	public SimpleBooleanProperty getWholesalerBought() {
		return wholesalerBought;
	}

	public void setWholesalerBought(SimpleBooleanProperty wholesalerBought) {
		this.wholesalerBought = wholesalerBought;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	

}
