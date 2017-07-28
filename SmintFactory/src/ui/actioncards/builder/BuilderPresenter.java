package ui.actioncards.builder;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Action;
import model.Model;
import model.card.Card;
import model.card.CardManager;

/**
 * Presenter class for bottom screen.
 */
public class BuilderPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button buildButton1, buildButton2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				buildButton1.setText(Integer.toString(model.getCurrentPlayer().getBuildCost().getValue()));
				buildButton2.setText(Integer.toString(model.getCurrentPlayer().getBuildCost().getValue()));
			}
		});
		
		model.getIsBuilderButton1Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				buildButton1.setDisable(model.getIsBuilderButton1Used().getValue());
			}
		});
		model.getIsBuilderButton2Used().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				buildButton2.setDisable(model.getIsBuilderButton2Used().getValue());
			}
		});
	}
	
	@FXML     
	private void actionBuild(ActionEvent event){
		model.doAction(Action.BUILD, ((Button)event.getSource()).getId().contains("1"));
    }
	
}
