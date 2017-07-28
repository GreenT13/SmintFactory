package ui.card;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.Action;
import model.Model;
import model.card.Card;

public class CardPresenter implements Initializable {
	
	@Inject
	Model model;
	
	Card card;
	
	@FXML Label cost, name, stars, extraMoney;
	@FXML Pane cardPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//
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
		backgroundColor(card.getColor());
	}
	
	void backgroundColor(String color){
		cardPane.setStyle(cardPane.getStyle() + "-fx-background-color: " + color + ";");
	}
	
	public Card getCard() {
		return card;
	}

	public void actionTake(MouseEvent event){
		if(((Pane)event.getSource()).getParent().getId().contains("plansBox")){
			model.clickCard(card.getCardId(), true, false);
		} else if(((Pane)event.getSource()).getParent().getId().contains("buildingsBox")){
			model.clickCard(card.getCardId(), false, true);
		} else if (((Pane)event.getSource()).getParent().getId().contains("cardBox")){
			model.clickCard(card.getCardId(), false, false);
		}
	}
	
	public void addBorder(MouseEvent event){
		if(model.getCurrentState() != null){
			int i;
			if(model.getPlayer(true).getPlayername() == model.getCurrentPlayer().getPlayername()){
				i = 1;
			} else{
				i = 2;
			}
			addBorder(((Pane)event.getSource()), model.getCurrentState().getAction(), i);
		}
	}
	
	public void removeBorder(MouseEvent event){
		((Pane)event.getSource()).getStyleClass().remove("pickBorder");
	}
	
	private void addBorder(Pane pane, Action action, int i){
			// Welke hebben cardBox nodig
		if(pane.getParent().getId().contains("cardBox")){
			if(action == Action.SUPPLY || action == Action.SWAP){
				pane.getStyleClass().add("pickBorder");
			}
			
			//Welke hebben plansBox nodig
		} else if(pane.getParent().getId().contains("plansBox"+i)){
			if(action == Action.RECYCLE || action == Action.SWAP || action == Action.BUILD){
				pane.getStyleClass().add("pickBorder");
			}
			
			//Welke hebben buildingsBox nodig
		} else if(pane.getParent().getId().contains("buildingsBox"+i)){
			if(action == Action.SWAP){
				pane.getStyleClass().add("pickBorder");
			}
		}
	}
}
