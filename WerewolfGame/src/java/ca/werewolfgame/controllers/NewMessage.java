/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.DAO;
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
                int currentRound = Integer.parseInt(request.getParameter("currentRound"));
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);
                dao.AddMessage(message, gameId);
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
                break;

            }
            case "wwMessage": {

                String playerId = user.getUsername();                
                int gameId = Integer.parseInt(request.getParameter("gameId"));                
                String role = request.getParameter("role");
                String status = request.getParameter("status");                
                int currentRound = Integer.parseInt(request.getParameter("currentRound"));
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);
                //System.out.println("Werewolf message in game: " + gameId);
                dao.AddMessage(message, gameId, "werewolf");
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
                break;

            }
            case "deadMessage": {

                String playerId = user.getUsername();                
                int gameId = Integer.parseInt(request.getParameter("gameId"));                
                String role = request.getParameter("role");
                String status = request.getParameter("status");                
                int currentRound = Integer.parseInt(request.getParameter("currentRound"));
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);
                dao.AddMessage(message, gameId, "deadChat");
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
                break;

            }
            default:
                break;
        }

    }

}
