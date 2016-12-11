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


@WebServlet(name = "NewMessage", urlPatterns = {"/NewMessage"})
public class NewMessage extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String text = (String)request.getAttribute("message");
        String chat = (String)request.getAttribute("sendMessage");
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
                int gameId = (int)request.getAttribute("gameId");                
                String role = (String)request.getAttribute("role");
                String status = (String)request.getAttribute("status");
                int currentRound = (int)request.getAttribute("currentRound");
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);
                dao.AddMessage(message, gameId);
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
                break;

            }
            case "wwMessage": {

                String playerId = user.getUsername();                
                int gameId = (int)request.getAttribute("gameId");                
                String role = (String)request.getAttribute("role");
                String status = (String)request.getAttribute("status");
                int currentRound = (int)request.getAttribute("currentRound");
                PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);
                //System.out.println("Werewolf message in game: " + gameId);
                dao.AddMessage(message, gameId, "werewolf");
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
                break;

            }
            case "deadMessage": {

                String playerId = user.getUsername();                
                int gameId = (int)request.getAttribute("gameId");                
                String role = (String)request.getAttribute("role");
                String status = (String)request.getAttribute("status");
                int currentRound = (int)request.getAttribute("currentRound");
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
