package ui.actioncards.supplier;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Action;
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
		
		model.getIsSupplierButton1Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				supplyButton1.setDisable(model.getIsSupplierButton1Used().getValue());
			}
		});
		model.getIsSupplierButton2Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				supplyButton2.setDisable(model.getIsSupplierButton2Used().getValue());
			}
		});

	}
	
	@FXML    
	private void actionSupply(ActionEvent event){
		model.doAction(Action.SUPPLY, ((Button)event.getSource()).getId().contains("1"));
    }	
}
