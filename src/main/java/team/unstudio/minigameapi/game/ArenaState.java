package team.unstudio.minigameapi.game;

/**
 * 竞技场状态
 */
public enum ArenaState {
	/**
	 * 初始化中
	 */
	INITALIZING,

	/**
	 * 等待游戏开始中
	 */
	WAITING,

	/**
	 * 游戏开始中
	 */
	STARTING,

	/**
	 * 游戏中
	 */
	PLAYING,

	/**
	 * 游戏结束中
	 */
	ENDING,

	/**
	 * 游戏不可用
	 */
	DISABLED;
}
