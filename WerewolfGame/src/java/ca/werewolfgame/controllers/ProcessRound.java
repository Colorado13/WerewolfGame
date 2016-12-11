/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.*;
import java.io.IOException;
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

@WebServlet(name = "ProcessRound", urlPatterns = {"/ProcessRound"})
public class ProcessRound extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //System.out.println("Process Round");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String playerId = user.getUsername();
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            String role = request.getParameter("role");
            String status = request.getParameter("status");
            int currentRound = Integer.parseInt(request.getParameter("currentRound"));
            PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);

            DAO dao = new DAO();

            ArrayList<Message> gameChatHistory = dao.getGameChat(playerInstance.getGameId());
            ArrayList<Message> wwChatHistory = dao.getWwChat(playerInstance.getGameId());
            ArrayList<Message> deadChatHistory = dao.getDeadChat(playerInstance.getGameId());

            ArrayList<String> alivePlayers = dao.getPlayers(playerInstance.getGameId());

            ArrayList<String> aliveVillagers = dao.getPlayers(playerInstance.getGameId(), "villagers");

            request.setAttribute("playerInstance", playerInstance);
            request.setAttribute("gameChatHistory", gameChatHistory);
            request.setAttribute("wwChatHistory", wwChatHistory);
            request.setAttribute("deadChatHistory", deadChatHistory);

            request.setAttribute("alivePlayers", alivePlayers);
            request.setAttribute("aliveVillagers", aliveVillagers);

            //dao.lynchPlayer(gameId, currentRound);
            dao.killPlayer(gameId, currentRound);
            dao.increaseRound(gameId);

            //dao.checkEndGame();
            request.getRequestDispatcher("GoToGamePage").forward(request, response); //

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ProcessRound.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
