package ui.players.abstractplayer;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Action;
import model.Model;

public abstract class PlayerPresenter implements Initializable {
	// Top label, will represent model.labelText.
	@FXML Label playerMoney, playerIncome, playerPoints, startingToken, playerName;
	
	@FXML Pane playerPane;
	
	@FXML Button passButton;
	@FXML TextField gameConsole;
	
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	protected Boolean isPlayer1;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerName.setText(model.getPlayer(isPlayer1).getPlayername());
		playerMoney.textProperty().bind(model.getPlayer(isPlayer1).getMoney().asString());
		playerIncome.textProperty().bind(model.getPlayer(isPlayer1).getIncome().asString());
		playerPoints.textProperty().bind(model.getPlayer(isPlayer1).getPoints().asString());

		model.getIsPlayer1StartPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && arg0.getValue().equals(isPlayer1)) {
					// Boolean is true.
					startingToken.setVisible(true);
				} else {
					// Boolean is false (or null)
					startingToken.setVisible(false);
				}
			}
		});
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && arg0.getValue().equals(isPlayer1)) {
					// Boolean is true.
					playerPane.getStyleClass().add("turn");
					passButton.setVisible(true);
					
				} else {
					// Boolean is false (or null)
					playerPane.getStyleClass().remove("turn");
					passButton.setVisible(false);
				}
			}
		});
		
		
	}
	
	@FXML 
	public void pass(ActionEvent event){
		model.doAction(Action.PASS, false);
	}
}
