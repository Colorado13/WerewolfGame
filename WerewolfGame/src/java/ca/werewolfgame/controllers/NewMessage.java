/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
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
@WebServlet(name = "NewMessage", urlPatterns = {"/NewMessage"})
public class NewMessage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String text = request.getParameter("message");
        String chat = request.getParameter("sendMessage");
        Message message = new Message(user.getUsername(), text);

        ArrayList<Message> chatHistory;
        ArrayList<Message> gameChatHistory;
        ArrayList<Message> wwChatHistory;
        ArrayList<Message> deadChatHistory;
        
        DAO dao = new DAO();

        //System.out.print("chat:" + chat);
        switch (chat) {
            case "mainChat":
                dao.AddMessage(message);

                try {
                    chatHistory = dao.getMainChat();
                    request.setAttribute("chatHistory", chatHistory);
                    request.getRequestDispatcher("MainPage.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            case "gameMessage": {

                String playerId = user.getUsername();
                int gameId = Integer.parseInt(request.getParameter("gameId"));                
                String role = request.getParameter("role");
                String status = request.getParameter("status");
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId);
                

                dao.AddMessage(message, gameId);

                try {
                    gameChatHistory = dao.getGameChat(gameId);
                    request.setAttribute("gameChatHistory", gameChatHistory);
                    request.setAttribute("playerInstance", playerInstance);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);

                } catch (SQLException ex) {
                    Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            }
            case "wwMessage": {

                String playerId = user.getUsername();                
                int gameId = Integer.parseInt(request.getParameter("gameId"));                
                String role = request.getParameter("role");
                String status = request.getParameter("status");                
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId);
                
                System.out.println("Werewolf message in game: " + gameId);
                dao.AddMessage(message, gameId, "werewolf");

                try {
                    wwChatHistory = dao.getWwChat(gameId);
                    gameChatHistory = dao.getGameChat(gameId);
                    request.setAttribute("gameChatHistory", gameChatHistory);
                    request.setAttribute("wwChatHistory", wwChatHistory);
                    request.setAttribute("playerInstance", playerInstance);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);

                } catch (SQLException ex) {
                    Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            }
            case "deadMessage": {

                String playerId = user.getUsername();                
                int gameId = Integer.parseInt(request.getParameter("gameId"));                
                String role = request.getParameter("role");
                String status = request.getParameter("status");                
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId);
                
                dao.AddMessage(message, gameId, "deadChat");

                try {
                    deadChatHistory = dao.getDeadChat(gameId);
                    gameChatHistory = dao.getGameChat(gameId);
                    request.setAttribute("gameChatHistory", gameChatHistory);
                    request.setAttribute("deadChatHistory", deadChatHistory);
                    request.setAttribute("playerInstance", playerInstance);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);

                } catch (SQLException ex) {
                    Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

            }
            default:
                break;
        }

    }

}
