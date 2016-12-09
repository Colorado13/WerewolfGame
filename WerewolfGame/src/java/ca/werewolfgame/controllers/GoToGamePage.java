/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.Message;
import ca.werewolfgame.beans.PlayerInstance;
import ca.werewolfgame.dao.DAO;
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
@WebServlet(name = "GoToGamePage", urlPatterns = {"/GoToGamePage"})
public class GoToGamePage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            DAO dao = new DAO();
            
            PlayerInstance playerInstance = (PlayerInstance)request.getAttribute("playerInstance");
            
            int gameId = (int)(session.getAttribute("currentGameId"));
            //System.out.println("Game ID at GoToGamePage: " + gameId);
            ArrayList<Message> gameChatHistory = dao.getGameChat(playerInstance.getGameId());
            ArrayList<Message> wwChatHistory = dao.getWwChat(playerInstance.getGameId());
            ArrayList<Message> deadChatHistory = dao.getDeadChat(playerInstance.getGameId());
            
            ArrayList<String> alivePlayers = dao.getPlayers(playerInstance.getGameId());
            ArrayList<String> aliveVillagers = dao.getPlayers(playerInstance.getGameId(),"villagers");    
            
            request.setAttribute("playerInstance", playerInstance);
            request.setAttribute("gameChatHistory", gameChatHistory);
            request.setAttribute("wwChatHistory", wwChatHistory);
            request.setAttribute("deadChatHistory", deadChatHistory);
            
            request.setAttribute("alivePlayers", alivePlayers);
            request.setAttribute("aliveVillagers", aliveVillagers);
            
            session.setAttribute("currentGameId", gameId);
            
            request.getRequestDispatcher("GamePage.jsp").forward(request, response);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GoToGamePage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
