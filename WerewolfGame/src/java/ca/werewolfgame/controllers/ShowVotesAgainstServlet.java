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
            // tally 
            out.println("<h5>Tally</h5>");
            out.println("<table>");
            
            for (int i = 0; i < allPlayers.size(); i++)
            {
                String player = allPlayers.get(i);
                ArrayList<Vote> votes = dao.getVotesAgainst(gameId, player, currentRound);
                ArrayList<Vote> lastVotesAgainst = dao.getLastVotesAgainst(gameId, player, currentRound);
                if (!votes.isEmpty())
                {
                    out.println("<tr>");
                    out.println("<td>" + player + "</td>");
                    out.println("<td>-</td>");
                    out.println("<td>" + lastVotesAgainst.size() + "</td>");
                    out.println("<td>-</td>");
                    for (int j = 0; j < lastVotesAgainst.size(); j++)
                    {
                        out.println("<td>" + lastVotesAgainst.get(j).getVotingId() + " [" + lastVotesAgainst.get(j).getVoteIndex() + "]</td>");
                    }
                    votes.removeAll(lastVotesAgainst);
                    for (int k = 0; k < votes.size(); k++)
                    {
                        out.println("<td><strike>" + votes.get(k).getVotingId() + " [" + votes.get(k).getVoteIndex() + "] </strike></td>");
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
