package player2;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class PlayerPresenter2 implements Initializable {
	
	// Top label, will represent model.labelText.
	@FXML
	Label playerMoney2, playerIncome2, playerPoints2, startingToken2;
	
	@FXML
	Pane playerPane2;
	
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button passButton2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerMoney2.textProperty().bind(model.getPlayer(false).getMoney().asString());
		playerIncome2.textProperty().bind(model.getPlayer(false).getIncome().asString());
		playerPoints2.textProperty().bind(model.getPlayer(false).getPoints().asString());

		model.getIsPlayer1StartPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && arg0.getValue()) {
					// Boolean is true.
					startingToken2.setVisible(false);
				} else {
					// Boolean is false (or null)
					startingToken2.setVisible(true);
				}
			}
		});
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if (arg0 != null && !arg0.getValue()) {
					// Boolean is true.
					playerPane2.getStyleClass().add("turn");
					playerPane2.getStyleClass().remove("noTurn");
					passButton2.setVisible(true);
					
				} else {
					// Boolean is false (or null)
					playerPane2.getStyleClass().add("noTurn");
					playerPane2.getStyleClass().remove("turn");
					passButton2.setVisible(false);
				}
			}
		});
	}
	
	public void pass(){
		model.pass();
	}
	
}
