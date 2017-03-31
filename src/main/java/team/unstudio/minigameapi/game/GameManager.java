package team.unstudio.minigameapi.game;

import java.util.Set;

import java.util.HashSet;

public class GameManager
{
    private static final Set<AbstractGame> games = new HashSet<>();
    
    public static void registerGame(AbstractGame game){
        if(games.contains(game)) throw new RuntimeException("This game has been registed.");
        
        games.add(game);
    }
    
    public static AbstractGame getGame(String name){
        for(AbstractGame game:games) 
        	if(game.getName().equals(name)) 
        		return game;
        return null;
    }
    
    public static Set<AbstractGame> getGames(){
    	return new HashSet<>(games);
    }
}
