package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowVotesAgainstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            DAO dao = new DAO();

            int gameId = (int)(session.getAttribute("currentGameId"));
            String playerId = ((User) request.getSession().getAttribute("user")).getUsername();
            
            PlayerInstance playerInstance = new PlayerInstance();

            playerInstance.setPlayerId(playerId);
            playerInstance.setRole(dao.getRole(gameId, playerId));
            playerInstance.setStatus(dao.getStatus(gameId, playerId));
            playerInstance.setCurrentRound(dao.getCurrentRound(gameId));
            playerInstance.setGameId(gameId);
            
            int currentRound = dao.getCurrentRound(gameId);
            ArrayList<String> allPlayers = dao.getPlayers(gameId);           
            PrintWriter out = response.getWriter();
            // tally (needs strikethroughs when it's not current index)
            out.println("<h5>Tally</h5>");
            out.println("<table>");
            HashMap<String, Integer> votesAgainst = (HashMap<String, Integer>)session.getAttribute("votesAgainst");
            
            /* THIS LOGIC IS NOT WORKING... REWORK! */
            for (int i = 0; i < allPlayers.size(); i++)
            {
                String player = allPlayers.get(i);
                ArrayList<Vote> votes = dao.getVotesAgainst(gameId, player, currentRound);
                if (!votes.isEmpty())
                {
                    out.println("<tr>");
                    out.println("<td>" + player + "</td>");
                    out.println("<td>-</td>");
                    if (votesAgainst.containsKey(allPlayers.get(i)))
                    {
                        out.println("<td>" + votesAgainst.get(allPlayers.get(i)) + "</td>");
                    }
                    out.println("<td>-</td>");
                    Integer totalVotesAgainstPlayer = 0;
                    int latestVoteIndex = 0;
                    for (int j = 0; j < allPlayers.size(); j++)
                    {
                        String currentPlayerToCheck = allPlayers.get(j);
                        for (int k = 0; k < votes.size(); k++)
                        {
                            if (currentPlayerToCheck.equals(votes.get(k).getVotingId()))
                            {
                                if (latestVoteIndex < votes.get(k).getVoteIndex())
                                {
                                    latestVoteIndex = votes.get(k).getVoteIndex();
                                }
                            }
                        }    
                    }
                    for (int l = 0; l < votes.size(); l++)
                        {
                            if (votes.get(l).getVoteIndex() != latestVoteIndex)
                            {
                                out.println("<td><strike>" + votes.get(l).getVotingId() + " [" + votes.get(l).getVoteIndex() + "] </strike></td>");        
                            }
                            else
                            {
                                out.println("<td>" + votes.get(l).getVotingId() + " [" + votes.get(l).getVoteIndex() + "]</td>");
                                totalVotesAgainstPlayer++;
                            }
                        }
                    System.out.println("Total Votes Against " + allPlayers.get(i) + " is " + totalVotesAgainstPlayer);
                    votesAgainst.put(allPlayers.get(i), totalVotesAgainstPlayer);
                }
            }
            out.println("</table>");
            session.setAttribute("votesAgainst", votesAgainst);
        } catch (SQLException ex) {
            Logger.getLogger(GamePageServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception!");
        }

    }

}
