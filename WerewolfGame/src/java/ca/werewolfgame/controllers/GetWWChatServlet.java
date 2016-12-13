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

public class GetWWChatServlet extends HttpServlet {

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

            ArrayList<Message> WwChatHistory = dao.getWwChat(gameId);
            //request.setAttribute("gameChatHistory", gameChatHistory);

            PrintWriter out = response.getWriter();

            out.println("<table>");
            for (int i = 0; i < WwChatHistory.size(); i++) {
                out.println("<tr>");
                out.println("<td>" + WwChatHistory.get(i).getUsername() + "</td>");
                out.println("<td>>></td>");
                out.println("<td>" + WwChatHistory.get(i).getMessage() + "</td>");
                out.println("<tr>");
            }
            out.println("</table>");
        } catch (SQLException ex) {
            Logger.getLogger(GamePageServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception!");
        }

    }

}
