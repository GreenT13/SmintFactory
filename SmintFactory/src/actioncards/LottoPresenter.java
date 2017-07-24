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
public class LottoPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button lottoButton1;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lottoButton1.setDisable(true);

		model.lottoBought.addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(model.lottoBought.getValue()){
					lottoButton1.setDisable(false);
				}
			}
			
		});
	}
	
	@FXML
    private void actionLotto(ActionEvent event){
		model.addGameConsoleText("Lotto doesn't work yet");
    }
}
