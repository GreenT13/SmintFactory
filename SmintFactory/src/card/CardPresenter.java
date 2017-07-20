package card;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import card.model.CardMapper;
import card.model.Type;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
	}
	
	
	
	/**
	 * Set all the values on the screen corresponding to the card.
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
		
		// Set the text of the labels.
		cost.setText(card.getCost().toString());
		name.setText(card.getName());
		stars.setText(card.getStars().toString());
		extraMoney.setText(card.getExtraMoney().toString());
		
		// Set the background color.
		if (card.getType().equals(Type.GREEN)) {
			cardPane.setId("paneGreen");
		} else if (card.getType().equals(Type.RED)) {
			cardPane.setId("paneRed");
		} else if (card.getType().equals(Type.YELLOW)) {
			cardPane.setId("paneYellow");
		}
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
}
