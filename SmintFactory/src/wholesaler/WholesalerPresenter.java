package wholesaler;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class WholesalerPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button wholesaleButton1, wholesaleButton2;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind model.labelText to the label.
		// Whenever the labelText changes, the label will change its value as well.
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				wholesaleButton1.setDisable(false);
				wholesaleButton2.setDisable(false);

				model.newRound.setValue(false);
			}
			
		});
		
	}
	
	@FXML
    private void actionWholesale(ActionEvent event){
		model.addGameConsoleText("Wholesaler doesn't work yet");
    }
	
	
	
}
