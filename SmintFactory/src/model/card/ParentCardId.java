package model.card;

import javafx.scene.Parent;

public class ParentCardId {
	
	Parent parent;
	Integer cardId;

	public ParentCardId(Parent parent, Integer cardId) {
		this.parent = parent;
		this.cardId = cardId;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	
	
}
