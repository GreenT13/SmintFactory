package supplier;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class SupplierPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button supplyButton1, supplyButton2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind model.labelText to the label.
		// Whenever the labelText changes, the label will change its value as well.
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				supplyButton1.setDisable(false);
				supplyButton2.setDisable(false);
				model.newRound.setValue(false);
			}
			
		});
		
		GetHandListener();

		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(supplyButton1.isDisabled()==false){
					supplyButton1.setText("");
					model.supplyButtonPressed.set(false);
				}
				if(supplyButton2.isDisabled()==false){
					supplyButton2.setText("");
					model.supplyButtonPressed.set(false);
				}	
			}
		});

	}
	
	@FXML
    private void actionSupply(ActionEvent event){
		System.out.println(model.getCurrentPlayer().getHand());
		if (((Button)event.getSource()).getText()==""){
			((Button)event.getSource()).setText("X");
			model.supplyButtonPressed.set(true);
		} else{
			((Button)event.getSource()).setText("");
			model.supplyButtonPressed.set(false);
		}	
    }
	
	void disableButton(Button button){
		button.setText("");
		button.setDisable(true);
		model.supplyButtonPressed.set(false);
	}
	
	void GetHandListener(){
		model.getBoard().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				if(supplyButton1.getText()=="X"){
					disableButton(supplyButton1);
				}
				if (supplyButton2.getText()=="X"){
					disableButton(supplyButton2);
				}
			}
		});
	}
	
}
