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

        if (chat.equals("mainChat")) {
            dao.AddMessage(message);

        }
        else if (chat.equals("gameMessage")) {
            //Get Game info...
            Game game = new Game();
            dao.AddMessage(message, game);

        }
        else if (chat.equals("wwMessage")) {
            //Get Game info...
            Game game = new Game();
            dao.AddMessage(message, game, "werewolf");

        }
        else if (chat.equals("deadMessage")) {
            //Get Game info...
            Game game = new Game();
            dao.AddMessage(message, game, "deadChat");

        }

        ArrayList<Message> chatHistory;
        try {
            chatHistory = dao.getMainChat();
            request.setAttribute("chatHistory", chatHistory);
            request.getRequestDispatcher("MainPage.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(NewMessage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
