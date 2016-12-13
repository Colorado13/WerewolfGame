/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.logic;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.DAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author jpedr
 */
public class GameChecker {

    public static String checkEndGame(Game game, int werewolves) {
        System.out.println(werewolves);
        if ((game.getPlayerCount() - werewolves) <= werewolves) {
            return "werewolf";
        }
        if (werewolves == 0) {
            return "good";
        } else {
            return "none";
        }

    }

    public static String getMostVoted(int gameId, int currentRound) throws SQLException {
        DAO dao = new DAO();
        ArrayList<Vote> lastVotes = dao.getLastVotes(gameId, currentRound);

        System.out.println("lastVotes before sorting:");
        for (int i = 0; i < lastVotes.size(); i++) {
            System.out.println(lastVotes.get(i).getVoteIndex());
        }

        Collections.sort(lastVotes, new Comparator<Vote>() {
            @Override
            public int compare(Vote v1, Vote v2) {
                return v1.getVoteIndex() - v2.getVoteIndex(); // Ascending
            }
        });

        System.out.println("lastVotes after sorting:");
        for (int i = 0; i < lastVotes.size(); i++) {
            System.out.println(lastVotes.get(i).getVoteIndex());
        }

        String playerToCheck;
        String mostVoted;
        int currentMostVotes = 0;

        for (int i = 0; i < lastVotes.size(); i++) {
            playerToCheck = lastVotes.get(i).getVotedId();
            int votes = 0;
            for (int j = 0; j < lastVotes.size(); j++) {
                if (lastVotes.get(j).getVotedId().equals(playerToCheck)) {
                    votes++;
                }
                if (votes > currentMostVotes) {

                }
            }

        }
        return "Player2";
    }

}
