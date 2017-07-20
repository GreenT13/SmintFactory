package player1;

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
import javafx.scene.layout.Pane;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class PlayerPresenter1 implements Initializable {
	
	// Top label, will represent model.labelText.
	@FXML
	Label playerMoney1, playerIncome1, playerPoints1, startingToken1;
	
	@FXML
	Pane playerPane1;
	
	@FXML Button passButton1;
	
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerMoney1.textProperty().bind(model.getPlayer(true).getMoney().asString());
		playerIncome1.textProperty().bind(model.getPlayer(true).getIncome().asString());
		playerPoints1.textProperty().bind(model.getPlayer(true).getPoints().asString());

		model.getIsPlayer1StartPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && arg0.getValue()) {
					// Boolean is true.
					startingToken1.setVisible(true);
				} else {
					// Boolean is false (or null)
					startingToken1.setVisible(false);
				}
			}
		});
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && arg0.getValue()) {
					// Boolean is true.
					playerPane1.getStyleClass().add("turn");
					playerPane1.getStyleClass().remove("noTurn");
					passButton1.setVisible(true);
					
				} else {
					// Boolean is false (or null)
					playerPane1.getStyleClass().add("noTurn");
					playerPane1.getStyleClass().remove("turn");
					passButton1.setVisible(false);
				}
			}
		});
		
		
	}
	
	@FXML
	public void pass(ActionEvent event){
		model.pass();
	}
	
}
