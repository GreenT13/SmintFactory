package builder;

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
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class BuilderPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;

	boolean buttonUsed=false;
	
	@FXML Button buildButton1, buildButton2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Bind model.labelText to the label.
		// Whenever the labelText changes, the label will change its value as well.
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				buildButton1.setDisable(false);
				buildButton2.setDisable(false);
				
				model.newRound.setValue(false);
			}
			
		});
		
		model.getCurrentPlayer().getBuildings().addListener(new ListChangeListener<Card>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> arg0) {
				// 
				if(buildButton1.getText()=="X"){
					buildButton1.setText("");
					buildButton1.setDisable(true);
				} else if (buildButton2.getText()=="X"){
					buildButton2.setText("");
					buildButton2.setDisable(true);
				}
				
			}
		});
		
		model.getIsPlayer1CurrentPlayer().addListener(new ChangeListener<Boolean>(){

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(buildButton1.isDisabled()==false){
					buildButton1.setText("2");
				}
				if(buildButton2.isDisabled()==false){
					buildButton2.setText("2");
				}	

			}
			
		});
		
	}
	
	@FXML
    private void actionBuild(ActionEvent event){
		if(model.getCurrentPlayer().getHand().size() > 0){
			if(buttonUsed == false){
				buttonUsed = true;
				if(model.getCurrentPlayer().getMoney().getValue()>1){
					((Button)event.getSource()).setText("X");
					model.builder(true);
					System.out.print(buttonUsed);
				} else {
					((Button)event.getSource()).setText("2");
					model.builder(false);
					System.out.print("Need more money");
				}		
			} else{
				buttonUsed = false;
				((Button)event.getSource()).setText("2");
			}
		} else {
			System.out.print("No cards in your hand");
		}
		
		
		
//		if(model.getCurrentPlayer().getMoney().getValue()>1){
//			((Button)event.getSource()).setDisable(true);
//			model.builder();
//		} else{
//			System.out.println("Need more money for " + ((Button)event.getSource()).getId());
//		}
		
    }
}
