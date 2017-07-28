package ui.actioncards.starter;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Action;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class StarterPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button startButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model.getIsStarterButtonUsed().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				startButton.setDisable(model.getIsStarterButtonUsed().getValue());
			}
		});
	}
	
	@FXML
    private void actionStart(ActionEvent event){
		model.doAction(Action.START, false);
    }
}
