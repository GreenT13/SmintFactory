package ui.actioncards.recycler;

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
public class RecyclerPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;

	@FXML Button recycleButton1;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model.getIsRecyclerButtonUsed().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				recycleButton1.setDisable(model.getIsRecyclerButtonUsed().getValue());
			}
		});
	}
	
	@FXML     
	private void actionRecycle(ActionEvent event){
		model.doAction(Action.RECYCLE, false);
    }
}
