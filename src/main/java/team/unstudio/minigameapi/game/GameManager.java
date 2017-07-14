package team.unstudio.minigameapi.game;

import java.util.Set;

import java.util.HashSet;

public class GameManager
{
    private static final Set<AbstractGame> GAMES = new HashSet<>();
    
    public static void registerGame(AbstractGame game){
        if(GAMES.contains(game)) throw new RuntimeException("This game has been registed.");
        
        GAMES.add(game);
    }
    
    public static AbstractGame getGame(String name){
        for(AbstractGame game:GAMES) 
        	if(game.getName().equals(name)) 
        		return game;
        return null;
    }
    
    public static Set<AbstractGame> getGames(){
    	return GAMES;
    }
}
