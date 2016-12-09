/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.beans;

/**
 *
 * @author jpedr
 */
public class Game implements java.io.Serializable{
    private int gameID;
    private int playerCount;

    public Game() {
    }

    public Game(int gameID, int playerCount) {
        this.gameID = gameID;
        this.playerCount = playerCount;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    
    
    
    
}
