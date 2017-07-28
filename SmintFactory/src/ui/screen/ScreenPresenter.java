package ui.screen;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.airhacks.afterburner.views.FXMLView;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import model.card.Card;
import model.card.CardManager;
import model.card.ParentCardId;
import ui.actioncards.builder.BuilderView;
import ui.actioncards.crowdfunder.CrowdfunderView;
import ui.actioncards.lotto.LottoView;
import ui.actioncards.producer.ProducerView;
import ui.actioncards.recycler.RecyclerView;
import ui.actioncards.starter.StarterView;
import ui.actioncards.supplier.SupplierView;
import ui.actioncards.swap.SwapView;
import ui.actioncards.temp.TempView;
import ui.actioncards.wholesaler.WholesalerView;
import ui.card.CardPresenter;
import ui.card.CardView;
import ui.players.player1.Player1View;
import ui.players.player2.Player2View;

/**
 * Main screen presenter.
 */
public class ScreenPresenter implements Initializable {
	
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML GridPane gridPane;
	
	@FXML VBox leftActionBox, rightActionBox;
	
	List<ParentCardId> boardList;
	List<ParentCardId> handListPlayer1;
	List<ParentCardId> handListPlayer2;
	List<ParentCardId> buildingsListPlayer1;
	List<ParentCardId> buildingsListPlayer2;
	List<FXMLView> actionList;
	
	Random rn = new Random();
	
	@FXML Button produceButton1, produceButton2, supplyButton1, supplyButton2, startButton, buildButton1, buildButton2;
	
	@FXML HBox cardBox, playerBox1, playerBox2;
	
	@FXML 
	TextArea gameConsole;
	
	@FXML HBox plansBox1, plansBox2, buildingsBox1, buildingsBox2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameConsole.textProperty().bind(model.getGameConsoleText());
		
		boardList = new ArrayList<ParentCardId>();
		handListPlayer1 = new ArrayList<ParentCardId>();
		handListPlayer2 = new ArrayList<ParentCardId>();
		buildingsListPlayer1 = new ArrayList<ParentCardId>();
		buildingsListPlayer2 = new ArrayList<ParentCardId>();
		actionList = new ArrayList<FXMLView>();
		
		//Player views
		FXMLView player1 = new Player1View();
		FXMLView player2 = new Player2View();
		
		//Action cards
		FXMLView producer = new ProducerView();
		FXMLView builder = new BuilderView();
		FXMLView supplier = new SupplierView();
		FXMLView starter = new StarterView();
		//Special action cards
		FXMLView wholesaler = new WholesalerView();	
		FXMLView crowdfunder = new CrowdfunderView();
		FXMLView temp = new TempView();	
		FXMLView swap = new SwapView();
		FXMLView lotto = new LottoView();
		FXMLView recycler = new RecyclerView();
		
		actionList.add(wholesaler);
		actionList.add(crowdfunder);
		actionList.add(temp);
		actionList.add(swap);
		actionList.add(lotto);
		actionList.add(recycler);
		
		//Layout standard
		leftActionBox.getChildren().add(producer.getView());
		leftActionBox.getChildren().add(starter.getView());
		rightActionBox.getChildren().add(supplier.getView());
		rightActionBox.getChildren().add(builder.getView());
		
		//Layout special cards	
		addSpecialCards(leftActionBox, rightActionBox);
		
		playerBox1.getChildren().add(player1.getView());
		playerBox2.getChildren().add(player2.getView());
		
		//Gets a three card board
		model.getBoard().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				updateBoardUI(arg0);
				
			}
		});
		
//		model.sevenPointsReached.addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				// TODO Auto-generated method stub
//				//gameConsole.getStyleClass().add("endGame");
//				model.addGameConsoleText("The winner is "+ model.determineWinner().getPlayername() + " with " + model.determineWinner().getPoints().getValue() + " points");
//			}
//		});


		plansChangeListener(true);
		plansChangeListener(false);
		buildingChangeListener(true);
		buildingChangeListener(false);
//		addSupplyBorderListener(true);
//		addSupplyBorderListener(false);
//		addBuildBorderListener(true);
//		addBuildBorderListener(false);
		
		updateBoardUI(null);
	}
	
	void addSpecialCards(VBox leftActionBox2, VBox rightActionBox2){
		int random;
		for(int i = 0; i<3; i++){
			random = rn.nextInt(actionList.size());
			rightActionBox2.getChildren().add(actionList.get(random).getView());
			actionList.remove(random);
		}
		for(int i = 0; i<3; i++){
			random = rn.nextInt(actionList.size());
			leftActionBox2.getChildren().add(actionList.get(random).getView());
			actionList.remove(random);
		}
	}
	
	void updateBoardUI(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
		if(arg0 == null){
			for(Card card:model.getBoard()){
				addCard(card.getCardId());
			}
			return;
		}
		arg0.next();
		for (Card c : arg0.getRemoved()) {
			// Voor elke kaart die verwijderd is, verwijder ook de kaart op het scherm.
			for (ParentCardId card : boardList) {
				if (card.getCardId().equals(c.getCardId())) {
					cardBox.getChildren().remove(card.getParent());
					boardList.remove(card);
					break;
				}
			}
		}
		for (Card card : arg0.getAddedSubList()) {
			// Voor elke kaart die toegevoegd is, voeg deze ook toe aan het scherm.
			addCard(card.getCardId());
		}
	}

	List<ParentCardId> getList(boolean isFirstPlayer, boolean plansList, boolean buildingsList) {
		if (isFirstPlayer & plansList) {
			return handListPlayer1;
		} else if (isFirstPlayer & buildingsList){
			return buildingsListPlayer1;
		} else if (!isFirstPlayer & plansList){
			return handListPlayer2;
		}else {
			return buildingsListPlayer2;
		}
	}
	

	HBox getBox(boolean isFirstPlayer, boolean isPlans, boolean isBuildings){
		if(isFirstPlayer & isPlans){
			return plansBox1;
		} else if (isFirstPlayer & isBuildings){
			return buildingsBox1;
		} else if (!isFirstPlayer & isPlans){
			return plansBox2;
		} else {
			return buildingsBox2;
		}
	}
	
	HBox getCardBox(){
		return cardBox;
	}
	
	void updateBox(javafx.collections.ListChangeListener.Change<? extends Card> arg0, boolean isFirstPlayer, boolean isPlans, boolean isBuildings) {
		List<ParentCardId> list = getList(isFirstPlayer, isPlans, isBuildings);
		HBox Box = getBox(isFirstPlayer, isPlans, isBuildings);
		arg0.next();
		for (Card c : arg0.getRemoved()) {
			// Voor elke kaart die verwijderd is, verwijder ook de kaart op het scherm.
			for (ParentCardId card : list) {
				if (card.getCardId().equals(c.getCardId())) {
					Box.getChildren().remove(card.getParent());
					list.remove(card);
					break;
				}
			}
		}
		for (Card card : arg0.getAddedSubList()) {
			// Voor elke kaart die toegevoegd is, voeg deze ook toe aan het scherm.
			FXMLView cardview = new CardView();
			((CardPresenter) cardview.getPresenter()).setCard(CardManager.retrieveCard(card.getCardId()));
			Box.getChildren().add(cardview.getView());
			list.add(new ParentCardId(cardview.getView(), ((CardPresenter) cardview.getPresenter()).getCard().getCardId()));
		}
	}
	
//	void addSupplyBorderListener(boolean isPlayer1){
//		model.getSupplyButtonPressed().addListener(new ChangeListener<Boolean>(){
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				// TODO Auto-generated method stub
//				if(model.getSupplyButtonPressed().getValue()==true){
//					addBorder(isPlayer1, true, false);
//				} else{
//					removeBorder(isPlayer1, true, false);
//				}
//				
//			}
//		});
//	}
	
//	void addBuildBorderListener(boolean isPlayer1){
//		model.getBuildButtonPressed().addListener(new ChangeListener<Boolean>(){
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				// TODO Auto-generated method stub
//				if(isPlayer1 == model.getIsPlayer1CurrentPlayer().getValue() & model.buildButtonPressed.getValue()){
//					addBorder(isPlayer1, false, true);
//				} else{
//					removeBorder(isPlayer1, false, true);
//				}
//				
//			}
//		});
//	}
	
	void addBorder(boolean isPlayer1, boolean onBoard, boolean inHand){
		if(onBoard){
			List<ParentCardId> list = getBoardList();
			for(ParentCardId card:list){
				card.getParent().getStyleClass().add("border");
			}
		} else if(inHand){
			List<ParentCardId> list = getList(isPlayer1, true, false);
			for(ParentCardId card:list){
				card.getParent().getStyleClass().add("border");
			}
		}
	}
	
	void removeBorder(boolean isPlayer1, boolean onBoard, boolean inHand){
		if(onBoard){
			List<ParentCardId> list = getBoardList();
			for(ParentCardId card:list){
				card.getParent().getStyleClass().remove("border");
			}
		} else if(inHand){
			List<ParentCardId> list = getList(isPlayer1, true, false);
			for(ParentCardId card:list){
				card.getParent().getStyleClass().remove("border");
			}
		}
	}	
	
	void addCard(Integer cardId) {
		FXMLView cardview = new CardView();
		((CardPresenter) cardview.getPresenter()).setCard(CardManager.retrieveCard(cardId));
		cardBox.getChildren().add(cardview.getView());		
		boardList.add(new ParentCardId(cardview.getView(), ((CardPresenter) cardview.getPresenter()).getCard().getCardId()));
	}
	

	void plansChangeListener(boolean isPlayer1){
		model.getPlayer(isPlayer1).getHand().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update plans view.
				updateBox(arg0, isPlayer1, true, false);
			}
		});
	}
	
	
	void buildingChangeListener(boolean isPlayer1){
		model.getPlayer(isPlayer1).getBuildings().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update buildings view.
				updateBox(arg0, isPlayer1, false, true);
			}
		});
	}

	public List<ParentCardId> getBoardList() {
		return boardList;
	}

	public void setBoardList(List<ParentCardId> boardList) {
		this.boardList = boardList;
	}

	

}
