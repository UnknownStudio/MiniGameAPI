package team.unstudio.minigameapi.currency;

public class NoEnoughMoneyException extends Exception{
	public NoEnoughMoneyException(String message){
		super(message);
	}
}
