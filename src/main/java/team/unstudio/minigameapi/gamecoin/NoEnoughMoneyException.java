package team.unstudio.minigameapi.gamecoin;

public class NoEnoughMoneyException extends Exception {
	public NoEnoughMoneyException(String message) {
		super(message);
	}
}
