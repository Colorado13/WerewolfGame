/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.logic;

import java.util.HashMap;

public class gameCreator {

    public static HashMap<String, String> setupGame(String[] players, int playerCount) {
        HashMap<String, String> gameSetup = new HashMap<>();
        int werewolfCount;
        if (playerCount < 8) {
            werewolfCount = 1;
        } else if (playerCount < 13) {
            werewolfCount = 2;
        } else if (playerCount < 18) {
            werewolfCount = 3;
        } else {
            werewolfCount = playerCount / 4;
        }

        while (werewolfCount != 0) {

            int possibleWw = (int) Math.floor(Math.random() * playerCount);
            //System.out.println("possible werewolf:" + possibleWw);
            if (!gameSetup.containsKey(players[possibleWw])) {
                {
                    gameSetup.put(players[possibleWw], "werewolf");
                    werewolfCount--;
                }
            }

        }
        for (int i = 0; i < players.length; i++) {
            if (!gameSetup.containsKey(players[i])) {
                gameSetup.put(players[i], "villager");
            }
        }
        /*
        for (int i = 0; i < gameSetup.size(); i++) {

            System.out.print(players[i]);
            System.out.println(gameSetup.get(players[i]));
        }
        */
        return gameSetup;

    }

}
