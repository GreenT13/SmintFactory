package actioncards;

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
public class CrowdfunderPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button crowdfundButton1, crowdfundButton2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				crowdfundButton1.setDisable(false);
				crowdfundButton2.setDisable(false);

				model.newRound.setValue(false);
			}
			
		});
	}
	
	@FXML
    private void actionCrowdfund(ActionEvent event){
		if(model.getCurrentPlayer().getMoney().getValue()>0){
	    	((Button)event.getSource()).setDisable(true);
	    	model.crowdfund();
		} else {
			model.addGameConsoleText("Need more money for" + ((Button)event.getSource()).getId());
		}
    }
}
