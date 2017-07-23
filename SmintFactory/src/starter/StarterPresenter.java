package starter;

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
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class StarterPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button startButton;
	
	@FXML
	private Label StartingToken1, StartingToken2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind model.labelText to the label.
		// Whenever the labelText changes, the label will change its value as well.
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				startButton.setDisable(false);

				model.newRound.setValue(false);
			}
			
		});
		
	}
	
	@FXML
    private void actionStart(ActionEvent event){
		if(model.getCurrentPlayer().getMoney().getValue()>0){
			((Button)event.getSource()).setDisable(true);
			model.starter();
		} else{
			model.addGameConsoleText("Need more money for " + ((Button)event.getSource()).getId());
		}
  	
    }
}
