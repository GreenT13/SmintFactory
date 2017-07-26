package card;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import card.model.CardMapper;
import card.model.Type;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Model;

public class CardPresenter implements Initializable {
	
	@Inject
	Model model;
	
	Card card;
	
	@FXML
	Label cost;
	
	@FXML
	Label name;
	
	@FXML
	Label stars;
	
	@FXML
	Label extraMoney;
	
	@FXML
	Pane cardPane;
	
	@FXML
	HBox plansBox1;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//	
//		model.supplyButtonPressed.addListener(new ChangeListener<Boolean>(){
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				// TODO Auto-generated method stub
//				if(model.supplyButtonPressed.getValue()==true){
//					cardPane.getStyleClass().add("pick");
//				} else{
//					cardPane.getStyleClass().remove("pick");
//				}
//			}
//			
//		});
	}
	
	
	
	/**
	 * Set all the values on the screen corresponding to the card.
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
		
		// Set the text of the labels.
		cost.setText("Cost: " + card.getCost().toString());
		name.setText(card.getName());
		stars.setText("Stars: " + card.getStars().toString());
		extraMoney.setText("Income: " + card.getExtraMoney().toString());
		
		// Set the background color.
		backgroundColor(card.getType());
	}
	
	void backgroundColor(String color){
		cardPane.setStyle(cardPane.getStyle() + "-fx-background-color: " + color + ";");
	}
	
	public Card getCard() {
		return card;
	}

	public void actionTake(MouseEvent event){
		if(model.swapButtonPressed.getValue()){
			if(((Pane)event.getSource()).getParent().getId().contains("plansBox")){
				// selecteert kaart om te swappen (swapOld)
				model.setSwapRemoveFromPlans(true);
				model.selectSwapCardHand(card.getCardId(), ((Pane)event.getSource()));
			} else if(((Pane)event.getSource()).getParent().getId().contains("buildingsBox")){
				// selecteert kaart om te swappen (swapOld)
				model.setSwapRemoveFromPlans(false);
				model.selectSwapCardHand(card.getCardId(), ((Pane)event.getSource()));
			} else if (((Pane)event.getSource()).getParent().getId().contains("cardBox")){
				// selecteert kaart uit cardbox (swapNew)
				model.selectSwapCardBoard(card.getCardId(), ((Pane)event.getSource()));
			}				
		} else if(model.recycleButtonPressed.getValue()){
			model.recycleCard(card.getCardId());
		} else if (model.supplyButtonPressed.getValue()){
			if (model.moneyCheck(model.getCurrentPlayer().getMoney().getValue(), CardMapper.createCard(card.getCardId()).getCost())){
				model.buyCard(card.getCardId());
			} else{
				model.addGameConsoleText("You don't have enough money to buy this card");
			}
		} else if(model.buildButtonPressed.getValue()){
			if (model.moneyCheck(model.getCurrentPlayer().getMoney().getValue(), 2)){
				model.buildCard(card.getCardId());
			} else{
				model.addGameConsoleText("You don't have enough money to build this card");
			}
		} else {
			model.addGameConsoleText("Press button first");
		}

	}
	
	public void toFrontCard(MouseEvent event){		
		addBorder(model.supplyButtonPressed.getValue(), ((Pane)event.getSource()), "cardBox");
		addBorder(model.buildButtonPressed.getValue(), ((Pane)event.getSource()), "plansBox");
		addBorder(model.recycleButtonPressed.getValue(), ((Pane)event.getSource()), "plansBox");
		addBorder(model.recycleButtonPressed.getValue(), ((Pane)event.getSource()), "buildingsBox");
		addBorder(model.swapButtonPressed.getValue(), ((Pane)event.getSource()), "cardBox");
		addBorder(model.swapButtonPressed.getValue(), ((Pane)event.getSource()), "plansBox");
		addBorder(model.swapButtonPressed.getValue(), ((Pane)event.getSource()), "buildingsBox");
	}
	
	public void toBackCard(MouseEvent event){
		((Pane)event.getSource()).getStyleClass().remove("pickBorder");
	}
	
	void addBorder(boolean buttonUsed, Pane source, String boxName){
		if (buttonUsed == true & source.getParent().getId().contains(boxName)){
			source.getStyleClass().add("pickBorder");
		}
	}
}
