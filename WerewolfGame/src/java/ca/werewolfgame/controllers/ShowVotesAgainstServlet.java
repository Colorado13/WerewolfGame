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
            PlayerInstance playerInstance = new PlayerInstance();
            int gameId = 0;
            String playerId = "Player";
            String role = "role";
            String status = "status";
            int currentRound = 0;
            
            if (session.getAttribute("currentGameId") != null) {
                gameId = (int) (session.getAttribute("currentGameId"));
            }
            if (request.getSession().getAttribute("user") != null) {
                playerId = ((User) request.getSession().getAttribute("user")).getUsername();
            }
            if (dao.getRole(gameId, playerId) != null) {
                role = dao.getRole(gameId, playerId);
            }
            if (dao.getCurrentRound(gameId) != 0) {
                currentRound = dao.getCurrentRound(gameId);
            }
            playerInstance.setPlayerId(playerId);
            playerInstance.setRole(role);
            playerInstance.setStatus(status);
            playerInstance.setCurrentRound(currentRound);
            playerInstance.setGameId(gameId);
            
            ArrayList<String> allPlayers = dao.getPlayers(gameId);           
            PrintWriter out = response.getWriter();
            // tally 
            out.println("<h5>Tally</h5>");
            out.println("<table>");
            
            for (int i = 0; i < allPlayers.size(); i++)
            {
                String player = allPlayers.get(i);
                ArrayList<Vote> lastVotes = dao.getLastVotes(gameId, currentRound);
                ArrayList<Vote> allVotes = dao.getAllVotes(gameId, currentRound);
                ArrayList<Vote> lastVotesAgainst = new ArrayList<>();
                ArrayList<Vote> allVotesAgainst = new ArrayList<>();
                for (int b = 0; b < lastVotes.size(); b++)
                {
                    if (lastVotes.get(b).getVotedId().equals(player))
                    {
                        lastVotesAgainst.add(lastVotes.get(b));
                    }
                }
                for (int l = 0; l < allVotes.size(); l++)
                {
                    if (allVotes.get(l).getVotedId().equals(player))
                    {
                        allVotesAgainst.add(allVotes.get(l));
                    }
                }
                if (!allVotes.isEmpty())
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
                    allVotesAgainst.removeAll(lastVotesAgainst);
                    for (int k = 0; k < allVotesAgainst.size(); k++)
                    {
                        out.println("<td><strike>" + allVotesAgainst.get(k).getVotingId() + " [" + allVotesAgainst.get(k).getVoteIndex() + "] </strike></td>");
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
