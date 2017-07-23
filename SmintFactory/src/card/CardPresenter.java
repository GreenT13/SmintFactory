package card;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import card.model.CardMapper;
import card.model.Type;
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
		model.supplyButtonPressed.addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				if(model.supplyButtonPressed.getValue()==true){
					cardPane.getStyleClass().add("pick");
				} else{
					cardPane.getStyleClass().remove("pick");
				}
			}
			
		});
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
		if (((Pane) event.getSource()).getParent().getId().contains("cardBox")) {
			// van cardBox --> plansBox
			model.clickCard(card.getCardId(), false, false, model.moneyCheck(model.getCurrentPlayer().getMoney().getValue(), CardMapper.createCard(card.getCardId()).getCost()));
		} else if(((Pane) event.getSource()).getParent().getId().contains("plansBox")){
			// van plansBox --> buildingsBox
			model.clickCard(card.getCardId(), true, false, model.moneyCheck(model.getCurrentPlayer().getMoney().getValue(), 2));
		} else {
			// true, true voert niks uit
			model.clickCard(card.getCardId(), true, true, true);
		}

	}
	
	public void toFrontCard(MouseEvent event){
		if(model.supplyButtonPressed.getValue()==true & ((Pane)event.getSource()).getParent().getId().contains("cardBox")){
			((Pane)event.getSource()).getStyleClass().add("pickBorder");
		}
		if (model.buildButtonPressed.getValue()==true & ((Pane)event.getSource()).getParent().getId().contains("plansBox")){
			((Pane)event.getSource()).getStyleClass().add("pickBorder");
		}
		
		
	}
	
	public void toBackCard(MouseEvent event){
		((Pane)event.getSource()).getStyleClass().remove("pickBorder");
//		if(((Pane)event.getSource()).getParent().getId().contains("plansBox")){
//			System.out.println("2222as");
//
//		}
	}
}
