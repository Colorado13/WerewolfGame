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
public class PlayerInstance implements java.io.Serializable{
    private String playerId;
    private String role;
    private String status;
    private int gameId;
    private int currentRound;

    public PlayerInstance() {
    }

    public PlayerInstance(String playerId, String role, String status, int gameId) {
        this.playerId = playerId;
        this.role = role;
        this.status = status;
        this.gameId = gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    public PlayerInstance(String playerId, String role, String status, int gameId, int currentRound) {
         this.playerId = playerId;
         this.role = role;
         this.status = status;
         this.gameId = gameId;
         this.currentRound = currentRound;
    } 
    
     public int getCurrentRound() {
      return currentRound;
     }
 
   public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }
}
