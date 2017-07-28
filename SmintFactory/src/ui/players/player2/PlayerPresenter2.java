package ui.players.player2;

import java.net.URL;
import java.util.ResourceBundle;

import ui.players.abstractplayer.PlayerPresenter;

/**
 * Presenter class for bottom screen.
 */
public class PlayerPresenter2 extends PlayerPresenter{
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isPlayer1= Boolean.FALSE;
		super.initialize(arg0, arg1);
	}
}
