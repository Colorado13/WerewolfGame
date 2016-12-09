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
@WebServlet(name = "MyGames", urlPatterns = {"/MyGames"})
public class MyGames extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String playerId = ((User) request.getSession().getAttribute("user")).getUsername();

        DAO dao = new DAO();
        ArrayList<Integer> myGames = new ArrayList<>();
        try {
            myGames = dao.getMyGames(playerId);
        } catch (SQLException ex) {
            Logger.getLogger(MyGames.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("myGames", myGames);
        request.getRequestDispatcher("MyGames.jsp").forward(request, response);

    }
}
