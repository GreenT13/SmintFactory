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
	
	public SimpleBooleanProperty handChange = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty buildingsChange = new SimpleBooleanProperty(false);
	
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
//				startButton.setDisable(false);
//				buildButton1.setDisable(false);
//				buildButton2.setDisable(false);
				model.newRound.setValue(false);
			}
			
		});
		
		model.getCurrentPlayer().getHand().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// Update board view.
				if(model.getSupplyButton1Used().getValue()==true){
					supplyButton1.setText("");
					supplyButton1.setDisable(true);
				}
				if (model.getSupplyButton2Used().getValue()==true){
					supplyButton2.setText("");
					supplyButton2.setDisable(true);
				}
				
			}
		});
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(supplyButton1.isDisabled()==false){
					supplyButton1.setText("");
				}
				if(supplyButton2.isDisabled()==false){
					supplyButton2.setText("");
				}	

			}
			
		});
		
		
	}
	
	@FXML
    private void actionSupply(ActionEvent event){
		if (((Button)event.getSource()).getText()==""){
			// Put X
			((Button)event.getSource()).setText("X");
			// Allow buying
			System.out.println(((Button)event.getSource()).getId());
			
			if(((Button)event.getSource()).getId()==supplyButton1.getId()){
				model.getSupplyButton1Used().set(true);
			} else if (((Button)event.getSource()).getId()==supplyButton2.getId()){
				model.getSupplyButton2Used().set(true);
			}
			model.supplier(true);
			
		} else{
			// Put ""
			((Button)event.getSource()).setText("");
			// Disallow buying
			if(((Button)event.getSource()).getId()==supplyButton1.getId()){
				model.getSupplyButton1Used().set(false);
			} else if (((Button)event.getSource()).getId()==supplyButton2.getId()){
				model.getSupplyButton2Used().set(false);
			}
			model.supplier(false);
		}
		System.out.println("button 1 "+ model.getSupplyButton1Used().getValue());
		System.out.println("button 2 "+ model.getSupplyButton2Used().getValue());
		
    }

	public void cardBorders(){
		
	}
	
	
}
