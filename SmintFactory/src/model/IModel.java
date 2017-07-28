package model;

public interface IModel {
	
	public void doAction(Action action, boolean usedButton1);
	
	public void clickCard(Integer cardId, boolean inPlans, boolean isBuilt);
	
}
