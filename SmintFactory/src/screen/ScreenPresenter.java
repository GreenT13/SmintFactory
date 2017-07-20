package screen;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.airhacks.afterburner.views.FXMLView;

import builder.BuilderView;
import card.CardPresenter;
import card.CardView;
import card.model.Card;
import card.model.CardId;
import card.model.CardMapper;
import card.model.ParentCardId;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import player1.Player1View;
import player2.Player2View;
import producer.ProducerView;
import starter.StarterView;
import supplier.SupplierView;

/**
 * Main screen presenter.
 */
public class ScreenPresenter implements Initializable {
	
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML
	GridPane gridPane;
	
	@FXML
	VBox leftActionBox, rightActionBox;
	
	List<ParentCardId> boardList;
	List<ParentCardId> handListPlayer1;
	List<ParentCardId> handListPlayer2;
	List<ParentCardId> buildingsListPlayer1;
	List<ParentCardId> buildingsListPlayer2;
	
	@FXML
	Button produceButton1, produceButton2, supplyButton1, supplyButton2, startButton, buildButton1, buildButton2;
	
	@FXML
	HBox cardBox, playerBox1, playerBox2, plansBox1, plansBox2, buildingsBox1, buildingsBox2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		boardList = new ArrayList<ParentCardId>();
		handListPlayer1 = new ArrayList<ParentCardId>();
		handListPlayer2 = new ArrayList<ParentCardId>();
		buildingsListPlayer1 = new ArrayList<ParentCardId>();
		buildingsListPlayer2 = new ArrayList<ParentCardId>();
		
		FXMLView player1 = new Player1View();
		FXMLView player2 = new Player2View();
		
		FXMLView producer = new ProducerView();
		FXMLView builder = new BuilderView();
		FXMLView supplier = new SupplierView();
		FXMLView starter = new StarterView();
		
		leftActionBox.getChildren().add(producer.getView());
		leftActionBox.getChildren().add(starter.getView());
		rightActionBox.getChildren().add(supplier.getView());
		rightActionBox.getChildren().add(builder.getView());
		playerBox1.getChildren().add(player1.getView());
		playerBox2.getChildren().add(player2.getView());
		
		model.getBoard().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				updateBoardUI(arg0);
				
			}
		});
		
		model.getPlayer(true).getHand().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update plans view.
				updatePlans(arg0, true);
			}
		});
		
		model.getPlayer(false).getHand().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update plans view.
				updatePlans(arg0, false);
			}
		});
		
		model.getPlayer(true).getBuildings().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update buildings view.
				updateBuildings(arg0, true);
			}
		});
		
		model.getPlayer(false).getBuildings().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update buildings view.
				updateBuildings(arg0, false);
			}
		});

		updateBoardUI(null);
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
	
	List<ParentCardId> getHandList(boolean isFirstPlayer) {
		if (isFirstPlayer) {
			return handListPlayer1;
		} else {
			return handListPlayer2;
		}
	}
	
	HBox getPlansBox(boolean isFirstPlayer) {
		if (isFirstPlayer) {
			return plansBox1;
		} else {
			return plansBox2;
		}
	}
	
	List<ParentCardId> getBuildingsList(boolean isFirstPlayer) {
		if (isFirstPlayer) {
			return buildingsListPlayer1;
		} else {
			return buildingsListPlayer2;
		}
	}
	
	HBox getBuildingsBox(boolean isFirstPlayer) {
		if (isFirstPlayer) {
			return buildingsBox1;
		} else {
			return buildingsBox2;
		}
	}
	
	void updatePlans(javafx.collections.ListChangeListener.Change<? extends Card> arg0, boolean isFirstPlayer) {
		List<ParentCardId> list = getHandList(isFirstPlayer);
		HBox plansBox = getPlansBox(isFirstPlayer);
		arg0.next();
		for (Card c : arg0.getRemoved()) {
			// Voor elke kaart die verwijderd is, verwijder ook de kaart op het scherm.
			for (ParentCardId card : list) {
				if (card.getCardId().equals(c.getCardId())) {
					plansBox.getChildren().remove(card.getParent());
					list.remove(card);
					break;
				}
			}
		}
		for (Card card : arg0.getAddedSubList()) {
			// Voor elke kaart die toegevoegd is, voeg deze ook toe aan het scherm.
			FXMLView cardview = new CardView();
			((CardPresenter) cardview.getPresenter()).setCard(CardMapper.createCard(card.getCardId()));
			plansBox.getChildren().add(cardview.getView());			
			list.add(new ParentCardId(cardview.getView(), ((CardPresenter) cardview.getPresenter()).getCard().getCardId()));
		}
	}
	
	void updateBuildings(javafx.collections.ListChangeListener.Change<? extends Card> arg0, boolean isFirstPlayer) {
		List<ParentCardId> list = getBuildingsList(isFirstPlayer);
		HBox buildingsBox = getBuildingsBox(isFirstPlayer);
		arg0.next();
		for (Card c : arg0.getRemoved()) {
			// Voor elke kaart die verwijderd is, verwijder ook de kaart op het scherm.
			for (ParentCardId card : list) {
				if (card.getCardId().equals(c.getCardId())) {
					buildingsBox.getChildren().remove(card.getParent());
					list.remove(card);
					break;
				}
			}
		}
		for (Card card : arg0.getAddedSubList()) {
			// Voor elke kaart die toegevoegd is, voeg deze ook toe aan het scherm.
			FXMLView cardview = new CardView();
			((CardPresenter) cardview.getPresenter()).setCard(CardMapper.createCard(card.getCardId()));
			buildingsBox.getChildren().add(cardview.getView());			
			list.add(new ParentCardId(cardview.getView(), ((CardPresenter) cardview.getPresenter()).getCard().getCardId()));
		}
	}

	
	void addCard(CardId cardId) {
		FXMLView cardview = new CardView();
		((CardPresenter) cardview.getPresenter()).setCard(CardMapper.createCard(cardId));
		cardBox.getChildren().add(cardview.getView());		
		boardList.add(new ParentCardId(cardview.getView(), ((CardPresenter) cardview.getPresenter()).getCard().getCardId()));
	}
	
	
	

}
