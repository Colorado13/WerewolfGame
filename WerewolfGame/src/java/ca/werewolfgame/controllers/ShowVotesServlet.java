package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowVotesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            DAO dao = new DAO();
            
            int gameId = 0;
            String playerId = "Player";
            if (session.getAttribute("currentGameId") != null) {
                gameId = (int) (session.getAttribute("currentGameId"));
            }
            if (request.getSession().getAttribute("user") != null) {
                playerId = ((User) request.getSession().getAttribute("user")).getUsername();
            }
            
            PlayerInstance playerInstance = new PlayerInstance();

            playerInstance.setPlayerId(playerId);
            playerInstance.setRole(dao.getRole(gameId, playerId));
            playerInstance.setStatus(dao.getStatus(gameId, playerId));
            playerInstance.setCurrentRound(dao.getCurrentRound(gameId));
            playerInstance.setGameId(gameId);
            
            int currentRound = dao.getCurrentRound(gameId);
            ArrayList<String> allPlayers = dao.getPlayers(gameId);           
            PrintWriter out = response.getWriter();
            out.println("<h5>Inverted Tally</h5>");
            out.println("<table>");
            // inverted tally
            for (int i = 0; i < allPlayers.size(); i++)
            {
                String player = allPlayers.get(i);
                ArrayList<Vote> votes = dao.getVotesBy(gameId, player, currentRound);
                if (votes.size() != 0)
                {
                    out.println("<tr>");
                    out.println("<td>" + player + "</td>");
                    out.println("<td>-</td>");
                    out.println("<td>" + votes.size() + "</td>");
                    out.println("<td>-</td>");
                    for (int j = 0; j < votes.size(); j++)
                    {
                        int latestVote = 0;
                        for (int l = 0; l < votes.size(); l++)
                        {
                            int currentVote = votes.get(l).getVoteIndex();
                            if (currentVote > latestVote)
                            {
                                latestVote = currentVote;
                            }
                        }
                       if (votes.get(j).getVoteIndex() != latestVote)
                       {
                           out.println("<td><strike>" + votes.get(j).getVotedId() + " [" + votes.get(j).getVoteIndex() + "] </strike></td>");    
                       }
                       else
                       {
                           out.println("<td>" + votes.get(j).getVotedId() + " [" + votes.get(j).getVoteIndex() + "]</td>");
                       }
                    }
                }
            }
            out.println("</table>");
            out.println("<h5>Tally</h5>");
            out.println("<table>");
            // tally (needs strikethroughs when it's not current index)
            for (int i = 0; i < allPlayers.size(); i++)
            {
                String player = allPlayers.get(i);
                ArrayList<Vote> votes = dao.getVotesAgainst(gameId, player, currentRound);
                if (votes.size() != 0)
                {
                    out.println("<tr>");
                    out.println("<td>" + player + "</td>");
                    out.println("<td>-</td>");
                    out.println("<td>" + votes.size() + "</td>");
                    out.println("<td>-</td>");
                    for (int j = 0; j < votes.size(); j++)
                    {
                        int latestVote = 0;
                        for (int l = 0; l < votes.size(); l++)
                        {
                            int currentVote = votes.get(l).getVoteIndex();
                            if (currentVote > latestVote)
                            {
                                latestVote = currentVote;
                            }
                        }
                       if (votes.get(j).getVoteIndex() != latestVote)
                       {
                           out.println("<td><strike>" + votes.get(j).getVotingId() + " [" + votes.get(j).getVoteIndex() + "] </strike></td>");    
                       }
                       else
                       {
                           out.println("<td>" + votes.get(j).getVotingId() + " [" + votes.get(j).getVoteIndex() + "]</td>");
                       }
                    }
                }
            }
            out.println("</table>");
        } catch (SQLException ex) {
            Logger.getLogger(GamePageServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception!");
        }

    }

}
