package actioncards;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
public class SwapPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button swapButton1;
	@FXML VBox leftActionBox, rightActionBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		model.newRoundListener(swapButton1);
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				swapButton1.setText("2");
			}
			
		});
		
		model.getBoard().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				if(swapButton1.getText()=="X"){
					swapButton1.setDisable(true);
				}
			}
		});
		
	}
	
	@FXML
    private void actionSwap(ActionEvent event){
		if(model.getCurrentPlayer().getHand().size() > 0 | model.getCurrentPlayer().getBuildings().size() > 0){
			if(((Button)event.getSource()).getText()!="X"){
				if(model.getCurrentPlayer().getMoney().getValue()>1){
					((Button)event.getSource()).setText("X");
					model.swap(true);
				} else {
					model.addGameConsoleText("Need more money");
				}		
			} else{
				((Button)event.getSource()).setText("2");
				model.swap(false);
			}
		} else {
			model.addGameConsoleText("No cards in your hand");
		}
    }
	

		

	
}

