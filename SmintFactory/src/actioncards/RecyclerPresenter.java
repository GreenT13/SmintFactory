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
public class RecyclerPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;

	boolean buttonUsed=false;
	
	@FXML Button recycleButton1;
	@FXML VBox leftActionBox, rightActionBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		model.newRoundListener(recycleButton1);
		
		GetHandListener(true);
		GetHandListener(false);
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(recycleButton1.isDisabled()==false){
					recycleButton1.setText("1");
					model.recycleButtonPressed.set(false);
				}
			}
			
		});
		//}
	}
	
	@FXML
    private void actionRecycle(ActionEvent event){
		if(model.getCurrentPlayer().getHand().size() > 0){
			if(((Button)event.getSource()).getText()!="X"){
				if(model.getCurrentPlayer().getMoney().getValue()>0){
					((Button)event.getSource()).setText("X");
					model.recycler(true);
				} else {
					model.addGameConsoleText("Need more money");
				}		
			} else{
				((Button)event.getSource()).setText("1");
				model.recycler(false);
			}
		} else {
			model.addGameConsoleText("No cards in your hand");
		}
    }
	
	void disableButton(Button button){
		button.setText("");
		button.setDisable(true);
		model.recycleButtonPressed.set(false);
	}
	
	void GetHandListener(boolean isPlayer1){
		model.getPlayer(isPlayer1).getHand().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				if(recycleButton1.getText()=="X"){
					disableButton(recycleButton1);
				}
			}
		});
	}
}
