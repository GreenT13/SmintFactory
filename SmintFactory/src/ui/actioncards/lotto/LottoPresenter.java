package ui.actioncards.lotto;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Action;
import model.Model;

/**
 * Presenter class for bottom screen.
 */
public class LottoPresenter implements Initializable {
	// Automatically use the same model for each .fxml using inject (also instantiates automatically).
	@Inject
	Model model;
	
	@FXML Button lottoButton1;
	@FXML TextField lottoText;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		model.getIsLottoButtonUsed().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				lottoButton1.setDisable(model.getIsLottoButtonUsed().getValue());
			}
		});
		
//		lottoButton1.setDisable(true);
//		lottoButton1.setVisible(false);
//		
//		model.getNewRound().addListener(new ChangeListener<Boolean>(){
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
//				// TODO Auto-generated method stub
//				if(model.lottoBought.getValue()==true){
//					lottoButton1.setDisable(false);
//				}			
//					model.newRound.setValue(false);	
//			}
//		});
//		
//		model.getPlayer(true).getBuildings().addListener(new ListChangeListener<Card>(){
//
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> c) {
//				// TODO Auto-generated method stub
//				if(model.getPlayer(true).getBuildings().contains(CardMapper.createCard(CardId.LOTTO))){
//					model.lottoBought.setValue(true);
//				}
//			}
//			
//		});
//		
//		model.getPlayer(false).getBuildings().addListener(new ListChangeListener<Card>(){
//
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Card> c) {
//				// TODO Auto-generated method stub
//				if(model.getPlayer(false).getBuildings().contains(CardMapper.createCard(CardId.LOTTO))){
//					model.lottoBought.setValue(true);
//				}
//			}
//			
//		});
//		
//		model.lottoBought.addListener(new ChangeListener<Boolean>(){
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if(model.lottoBought.getValue()){
//					lottoButton1.setDisable(false);
//					lottoButton1.setVisible(true);
//					lottoText.setText("changetext");
//				} else{
//					lottoButton1.setDisable(true);
//					lottoButton1.setVisible(false);
//					lottoText.setText("Lotto is not available");
//				}
//			}
//			
//		});
	
		
	}
	
	@FXML
	private void actionLotto(ActionEvent event){
		model.doAction(Action.LOTTO, false);
    }
}
