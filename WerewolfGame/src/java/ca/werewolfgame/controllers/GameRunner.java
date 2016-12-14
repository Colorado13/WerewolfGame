/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.dao.*;
import ca.werewolfgame.beans.*;
import ca.werewolfgame.logic.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpedr
 */
@WebServlet(name = "GameRunner", urlPatterns = {"/GameRunner"})
public class GameRunner extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();

        while (true) {
            try {
                ArrayList<Integer> activeGames = dao.GetActiveGames();

                for (int i = 0; i < activeGames.size(); i++) {
                    try {
                        System.out.println("Checking game: " + activeGames.get(i));

                        Game game = new Game();

                        game.setGameID(activeGames.get(i));
                        game.setCurrentRound(dao.getCurrentRound(game.getGameId()));
                        game.setPlayerCount(dao.getPlayerCount(game.getGameId()));
                        game.setGameStatus("active");
                        System.out.println("Elapsed Time: " + dao.getElapsedTime(game.getGameId()));
                        if (dao.getElapsedTime(game.getGameId()) >= GameParameters.getRoundDuration) {
                        //if (true) {
                            int werewolves = 0;
                            ArrayList<String> playerIds = dao.getPlayers(game.getGameId());
                            dao.lynchPlayer(game.getGameId(), game.getCurrentRound());
                            System.out.println("Lynching Player");
                            for (int j = 0; j < playerIds.size(); j++) {
                                System.out.println("Player: " + playerIds.get(j) + " role: " + dao.getRole(game.getGameId(), playerIds.get(j)));
                                if (dao.getRole(game.getGameId(), playerIds.get(j)).equals("werewolf")) {
                                    werewolves++;
                                }
                            }
                            System.out.println("Alive werewolves: " + werewolves);
                            String check = GameChecker.checkEndGame(game, werewolves);

                            if (!check.equals("none")) {
                                dao.endGame(game.getGameId(), check);
                            }

                            dao.killPlayer(game.getGameId(), game.getCurrentRound());

                            System.out.println("Killing Player");

                            playerIds = dao.getPlayers(game.getGameId());
                            werewolves = 0;
                            for (int j = 0; j < playerIds.size(); j++) {
                                if (dao.getRole(game.getGameId(), playerIds.get(j)).equals("werewolf")) {
                                    werewolves++;
                                }
                            }

                            System.out.println("Checking for endgame");

                            check = GameChecker.checkEndGame(game, werewolves);

                            if (!check.equals("none")) {
                                dao.endGame(game.getGameId(), check);

                            }

                            System.out.println("Increasing Round");
                            dao.increaseRound(game.getGameId());

                        }

                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                System.out.println("Sleeping!");
                TimeUnit.SECONDS.sleep(15);
                System.out.println("Waking up!");
            } catch (SQLException | InterruptedException ex) {
                Logger.getLogger(GameRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
