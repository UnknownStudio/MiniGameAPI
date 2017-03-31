package team.unstudio.minigameapi.game;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.util.Map;
import org.bukkit.Bukkit;
import team.unstudio.minigameapi.event.GamePlayerJoinEvent;
import team.unstudio.minigameapi.event.GamePlayerLeaveEvent;
import team.unstudio.minigameapi.event.GameStartEvent;
import team.unstudio.minigameapi.event.GameEndEvent;
import team.unstudio.minigameapi.event.GameStopEvent;
import team.unstudio.minigameapi.util.EntityGroup;

import java.util.HashMap;

public class Room extends BukkitRunnable implements ConfigurationSerializable {
	private static final Map<Player, Room> PlayerToRoom = new HashMap<>();

	public static boolean isInGame(Player player) {
		return PlayerToRoom.containsKey(player);
	}

	public static Room getRoom(Player player) {
		return PlayerToRoom.get(player);
	}

	private final AbstractGame game;

	private String name;
	protected EntityGroup<Player> players = new EntityGroup<>();
	private RoomState state = RoomState.DISABLED;
	private long tick = 0;

	public Room(AbstractGame game, String name) {
		this.game = game;
		this.name = name;
	}

	public Room(Map<String, Object> map) {
		this(GameManager.getGame((String) map.get("game")), (String) map.get("name"));
	}

	public static Room deserialize(Map<String, Object> map) {
		return new Room(map);
	}

	public static Room valueOf(Map<String, Object> map) {
		return new Room(map);
	}

	public EntityGroup<Player> getPlayers() {
		return players;
	}

	public AbstractGame getGame() {
		return game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomState getState() {
		return state;
	}

	public boolean joinPlayer(Player player) {
		if (players.contains(player))
			return true;

		if (state != RoomState.WAITING)
			return false;

		Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent(this, player));

		game.onPlayerJoin(this, player);

		players.add(player);

		PlayerToRoom.put(player, this);

		return true;
	}

	public boolean leavePlayer(Player player) {
		if (!players.contains(player))
			return true;

		Bukkit.getPluginManager().callEvent(new GamePlayerLeaveEvent(this, player));

		game.onPlayerLeave(this, player);

		players.remove(player);

		PlayerToRoom.remove(player);

		return true;
	}

	public void setEnable(boolean enable) {
		state = enable ? RoomState.WAITING : RoomState.DISABLED;
	}

	public boolean isEnable() {
		return state != RoomState.DISABLED;
	}

	public void start() {
		synchronized (this) {
			if (state != RoomState.WAITING)
				return;
			if (game.onGamePreStart(this))
				state = RoomState.STARTING;
			else
				return;
		}
		tick = 0;

		Bukkit.getPluginManager().callEvent(new GameStartEvent(this));

		game.onGamePostStart(this);

		runTaskTimer(getGame().getPlugin(), 1L, 1L);

		state = RoomState.PLAYING;
	}

	public void stop() {
		synchronized (this) {
			state = RoomState.ENDING;
		}

		cancel();

		Bukkit.getPluginManager().callEvent(new GameStopEvent(this));

		game.onGameStop(this);

		state = RoomState.WAITING;
	}

	public void end() {
		synchronized (this) {
			if (state != RoomState.PLAYING)
				return;
			state = RoomState.ENDING;
		}

		cancel();

		Bukkit.getPluginManager().callEvent(new GameEndEvent(this));

		game.onGameEnd(this);

		state = RoomState.WAITING;
	}

	public void dispose() {
		if (isEnable()) {
			stop();
			setEnable(false);
		}
	}

	public long getTick() {
		return tick;
	}

	@Override
	public void run() {
		tick++;
		game.onGameTick(this);
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
		if (obj == null)
			return false;

		if (!(obj instanceof Room))
			return false;

		Room o = (Room) obj;

		if (!o.getName().equals(getName()))
			return false;

		if (!o.getGame().equals(getGame()))
			return false;

		return true;
	}
}
