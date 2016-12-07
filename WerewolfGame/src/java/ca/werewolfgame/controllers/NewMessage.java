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
        
        DAO dao = new DAO();

        Message message = new Message(user.getUsername(), text);

        switch (chat) {
            case "mainChat":
                dao.AddMessage(message);
                ArrayList<Message> chatHistory;
                try {
                    chatHistory = dao.getMainChat();
                    request.setAttribute("chatHistory", chatHistory);
                    request.getRequestDispatcher("MainPage.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
                }       break;
            case "gameMessage":
                {
                    //Get Game info...
                    Game game = new Game();
                    dao.AddMessage(message, game);
                    ArrayList<Message> gameChatHistory;
                    gameChatHistory = dao.getGameChat();
                    request.setAttribute("gameChatHistory", gameChatHistory);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);
                    break;
                }
            case "wwMessage":
                {
                    //Get Game info...
                    Game game = new Game();
                    dao.AddMessage(message, game, "werewolf");
                    ArrayList<Message> wwChatHistory;
                    wwChatHistory = dao.getWwChat();
                    request.setAttribute("gameChatHistory", wwChatHistory);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);
                    break;
                }
            case "deadMessage":
                {
                    //Get Game info...
                    Game game = new Game();
                    dao.AddMessage(message, game, "deadChat");
                    ArrayList<Message> deadChatHistory;
                    deadChatHistory = dao.getDeadChat();
                    request.setAttribute("gameChatHistory", deadChatHistory);
                    request.getRequestDispatcher("GamePage.jsp").forward(request, response);
                    break;
                }
            default:
                break;
        }

        

    }

}
