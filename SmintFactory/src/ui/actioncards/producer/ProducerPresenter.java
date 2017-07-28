package ui.actioncards.producer;

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
public class ProducerPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button produceButton1, produceButton2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		model.getIsProducerButton1Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				produceButton1.setDisable(model.getIsProducerButton1Used().getValue());
			}
		});
		model.getIsProducerButton2Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				produceButton2.setDisable(model.getIsProducerButton2Used().getValue());
			}
		});
		
	}
	
	@FXML
	private void actionProduce(ActionEvent event){
		model.doAction(Action.PRODUCE, ((Button)event.getSource()).getId().contains("1"));
    }
	
	
	
}
