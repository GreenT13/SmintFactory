package card.model;

import javafx.scene.Parent;

public class ParentCardId {
	
	Parent parent;
	CardId cardId;

	public ParentCardId(Parent parent, CardId cardId) {
		this.parent = parent;
		this.cardId = cardId;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public CardId getCardId() {
		return cardId;
	}

	public void setCardId(CardId cardId) {
		this.cardId = cardId;
	}
	
	
}
