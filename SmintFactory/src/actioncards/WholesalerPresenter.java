package actioncards;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import card.model.Card;
import card.model.CardId;
import card.model.CardMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class WholesalerPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button wholesaleButton1;
	@FXML VBox leftActionBox, rightActionBox;
	@FXML TextField wholesaleText;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		wholesaleButton1.setDisable(true);
		wholesaleButton1.setVisible(false);
		
		model.getNewRound().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				// TODO Auto-generated method stub
				if(model.wholesalerBought.getValue()==true){
					wholesaleButton1.setDisable(false);
				}			
					model.newRound.setValue(false);	
			}
		});

		model.getPlayer(true).getBuildings().addListener(new ListChangeListener<Card>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> c) {
				// TODO Auto-generated method stub
				if(model.getPlayer(true).getBuildings().contains(CardMapper.createCard(CardId.WHOLESALER))){
					model.wholesalerBought.setValue(true);
				}
			}
			
		});
		
		model.getPlayer(false).getBuildings().addListener(new ListChangeListener<Card>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> c) {
				// TODO Auto-generated method stub
				if(model.getPlayer(false).getBuildings().contains(CardMapper.createCard(CardId.WHOLESALER))){
					model.wholesalerBought.setValue(true);
				}
			}
			
		});
		
		model.wholesalerBought.addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(model.wholesalerBought.getValue()){
					wholesaleButton1.setDisable(false);
					wholesaleButton1.setVisible(true);
					wholesaleText.setText("Wganjdfanjs dla.");
				} else{
					wholesaleButton1.setDisable(true);
					wholesaleButton1.setVisible(false);
					wholesaleText.setText("Wholesaler is not available yet.");
				}
			}
			
		});
	
		
	}
	
	@FXML
    private void actionWholesale(ActionEvent event){
		model.addGameConsoleText("Wholesaler doesn't work yet");
    }
	
	
	
}
