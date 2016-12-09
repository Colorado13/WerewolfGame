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

public class GetDeadChatServlet extends HttpServlet {

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
            playerInstance.setGameId(gameId);

            ArrayList<Message> DeadChatHistory = dao.getDeadChat(gameId);
            //request.setAttribute("gameChatHistory", gameChatHistory);
            
            PrintWriter out = response.getWriter();
            
            out.println("<table>");
            for (int i = 0; i < DeadChatHistory.size(); i++)
            {
                out.println("<tr>");
                out.println("<td>" + DeadChatHistory.get(i).getUsername() + "</td>");
                out.println("<td>>></td>");
                out.println("<td>" + DeadChatHistory.get(i).getMessage() + "</td>");
                out.println("<tr>");
            }
            out.println("</table>");
        } catch (SQLException ex) {
            Logger.getLogger(GamePageServlet.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception!");
        }

    }

}
