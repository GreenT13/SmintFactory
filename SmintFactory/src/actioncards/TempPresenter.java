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
import javafx.scene.layout.VBox;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class TempPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button tempButton1;
	@FXML VBox leftActionBox, rightActionBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		model.newRoundListener(tempButton1);
		
	}
	
	@FXML
    private void actionTemp(ActionEvent event){
		model.addGameConsoleText("Temp Agency doesn't work yet");
    }
}
