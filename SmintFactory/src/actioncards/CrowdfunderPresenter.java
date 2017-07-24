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
import javafx.scene.layout.VBox;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class CrowdfunderPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button crowdfundButton1;
	@FXML VBox leftActionBox, rightActionBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		model.newRoundListener(crowdfundButton1);
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
