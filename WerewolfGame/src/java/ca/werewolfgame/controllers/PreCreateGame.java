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

/**
 *
 * @author jpedr
 */
@WebServlet(name = "PreCreateGame", urlPatterns = {"/PreCreateGame"})
public class PreCreateGame extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO dao = new DAO();
        ArrayList<String> playerRoster;
        try {
            playerRoster = dao.getPlayers();
            request.setAttribute("playerRoster", playerRoster);
            request.getRequestDispatcher("CreateGame.jsp").forward(request, response);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(PreCreateGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }

    

}
