/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author jpedr
 */
@WebServlet(name = "GamePageServlet", urlPatterns = {"/GamePageServlet"})
public class GamePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            DAO dao = new DAO();

            int gameId = Integer.parseInt(request.getParameter("game"));
            //System.out.println("Game ID at GamePageServlet: " + gameId);
            session.setAttribute("currentGameId", gameId);
            String playerId = ((User) request.getSession().getAttribute("user")).getUsername();

            PlayerInstance playerInstance = new PlayerInstance();

            playerInstance.setPlayerId(playerId);
            playerInstance.setRole(dao.getRole(gameId, playerId));
            playerInstance.setStatus(dao.getStatus(gameId, playerId));
            playerInstance.setGameId(gameId);
            playerInstance.setCurrentRound(dao.getCurrentRound(gameId));

            ArrayList<Message> gameChatHistory = dao.getGameChat(gameId);
            request.setAttribute("gameChatHistory", gameChatHistory);

            if (playerInstance.getStatus().equals("DEAD")) {
                ArrayList<Message> deadChatHistory = dao.getDeadChat(gameId);
                request.setAttribute("deadChatHistory", deadChatHistory);
            } 
            else if (playerInstance.getRole().equals("werewolf")) {
                ArrayList<Message> wwChatHistory = dao.getWwChat(gameId);
                request.setAttribute("wwChatHistory", wwChatHistory);
            }

            request.setAttribute("playerInstance", playerInstance);
            request.setAttribute("gameId", gameId);
            request.getRequestDispatcher("GoToGamePage").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(GamePageServlet.class.getName()).log(Level.SEVERE, null, ex);
       }

    }

}
