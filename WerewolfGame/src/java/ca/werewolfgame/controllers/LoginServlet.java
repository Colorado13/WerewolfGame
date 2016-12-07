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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String username = request.getParameter("name");
        String password = request.getParameter("password");
        DAO dao = new DAO();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPrivilege("MEMBER");

        String option = request.getParameter("option");

        if (option.equals("createUser")) {
            boolean existingUser = false;
            try {
                ArrayList<User> usersList = dao.GetUser();
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getUsername().equals(user.getUsername())) {

                        existingUser = true;
                    }

                }

                if (existingUser) {
                    //TODO Redirect to index page with username already take message
                    response.sendRedirect("index.jsp");
                } else {
                    dao.AddUser(user);
                    session.setAttribute("user", user);
                    
                    ArrayList<Message> chatHistory = dao.getMainChat();
                    request.setAttribute("chatHistory", chatHistory);
                    request.getRequestDispatcher("MainPage.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                ArrayList<User> usersList = dao.GetUser();
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getUsername().equals(user.getUsername())) {
                        if (usersList.get(i).getPassword().equals(user.getPassword())) {
                            System.out.println("Login Successful");
                            user.setPrivilege(usersList.get(i).getPrivilege());
                            session.setAttribute("user", user);

                            ArrayList<Message> chatHistory = dao.getMainChat();
                            request.setAttribute("chatHistory", chatHistory);
                            request.getRequestDispatcher("MainPage.jsp").forward(request, response);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}