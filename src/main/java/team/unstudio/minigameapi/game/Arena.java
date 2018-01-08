package team.unstudio.minigameapi.game;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Maps;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import team.unstudio.minigameapi.event.GamePlayerJoinEvent;
import team.unstudio.minigameapi.event.GamePlayerLeaveEvent;
import team.unstudio.minigameapi.event.GameStartEvent;
import team.unstudio.minigameapi.event.GameEndEvent;
import team.unstudio.minigameapi.event.GameSuspendEvent;
import team.unstudio.minigameapi.util.EntityGroup;
import team.unstudio.udpl.util.PluginUtils;

import java.util.HashMap;

public abstract class Arena implements ConfigurationSerializable {

	private static final Map<Player, Arena> PLAYER_TO_ROOM = Maps.newHashMap();

	public static boolean isInArena(Player player) {
		return PLAYER_TO_ROOM.containsKey(player);
	}

	public static Arena getArena(Player player) {
		return PLAYER_TO_ROOM.get(player);
	}

	private final Game game;
	private final String name;

	private ArenaState state = ArenaState.DISABLED;
	private GameLoop gameLoop;
	private long tick = 0;

	private EntityGroup<Player> players = new EntityGroup<>();

	public Arena(Game game, String name) {
		Validate.notNull(game);
		Validate.notEmpty(name);
		this.game = game;
		this.name = name;
	}

	public Arena(Map<String, Object> map) {
		this(GameManager.getGame((String) map.get("game")), (String) map.get("name"));
	}

	public final EntityGroup<Player> getPlayers() {
		return players;
	}

	public final Game getGame() {
		return game;
	}

	public final String getName() {
		return name;
	}

	public final ArenaState getState() {
		return state;
	}

	public final boolean isWaiting() {
		return getState() == ArenaState.WAITING;
	}

	public final boolean isPlaying() {
		return getState() == ArenaState.PLAYING;
	}

	public final boolean isEnable() {
		return getState() != ArenaState.DISABLED;
	}

	protected final void setState(ArenaState state) {
		this.state = state;
	}

	public final long getTick() {
		return tick;
	}

	public boolean joinPlayer(Player player) {
		if (players.contains(player))
			return true;

		if (!isWaiting())
			return false;

		if (PluginUtils.callEventIsCancelled(new GamePlayerJoinEvent(this, player)))
			return false;

		game.onPlayerJoin(this, player);
		players.add(player);
		PLAYER_TO_ROOM.put(player, this);
		return true;
	}

	public boolean leavePlayer(Player player) {
		if (!players.contains(player))
			return true;

		if (PluginUtils.callEventIsCancelled(new GamePlayerLeaveEvent(this, player)))
			return false;

		game.onPlayerLeave(this, player);
		players.remove(player);
		PLAYER_TO_ROOM.remove(player);
		return true;
	}

	public void enable() {
		if (isEnable())
			return;
		init();
	}

	public void disable() {
		if (!isEnable())
			return;
		suspend();
		setState(ArenaState.DISABLED);
	}

	public void init() {
		setState(ArenaState.INITALIZING);

		game.onGameInitalize(this);

		setState(ArenaState.WAITING);
	}

	public void start() {
		if (!isWaiting())
			return;

		if (!game.onGamePreStart(this))
			return;

		setState(ArenaState.STARTING);

		GameStartEvent event = new GameStartEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled())
			return;

		tick = 0;
		game.onGameStarted(this);
		gameLoop.runTaskTimer(getGame().getPlugin(), 1L, 1L);
		setState(ArenaState.PLAYING);
	}

	public void suspend() {
		if (!isPlaying())
			return;
		setState(ArenaState.ENDING);

		gameLoop.cancel();
		game.onGameSuspend(this);
		Bukkit.getPluginManager().callEvent(new GameSuspendEvent(this));
		setState(ArenaState.WAITING);
	}

	public void end() {
		if (!isPlaying())
			return;
		setState(ArenaState.ENDING);

		gameLoop.cancel();
		game.onGameEnd(this);
		Bukkit.getPluginManager().callEvent(new GameEndEvent(this));
		setState(ArenaState.WAITING);
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("game", game.getName());
		map.put("name", name);
		return map;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Arena))
			return false;

		Arena arena = (Arena) obj;

		return arena.getGame().equals(getGame()) && arena.getName().equals(getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getGame(), getName());
	}

	private class GameLoop extends BukkitRunnable {

		@Override
		public void run() {
			tick++;
			game.onGameTick(Arena.this);
		}

	}
}
