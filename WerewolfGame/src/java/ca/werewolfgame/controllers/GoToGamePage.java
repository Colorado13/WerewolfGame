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
            DAO dao = new DAO();
            
            PlayerInstance playerInstance = (PlayerInstance)request.getAttribute("playerInstance");            
            
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
            
            request.getRequestDispatcher("GamePage.jsp").forward(request, response);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GoToGamePage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
