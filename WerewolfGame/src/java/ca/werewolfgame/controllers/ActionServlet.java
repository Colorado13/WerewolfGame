/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.dao.*;
import ca.werewolfgame.logic.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String action = request.getParameter("action");
            String selectedPlayer = request.getParameter("selectedPlayer");
            String playerId = user.getUsername();
            int gameId = Integer.parseInt(request.getParameter("gameId"));
            String role = request.getParameter("role");
            String status = request.getParameter("status");
            int currentRound = Integer.parseInt(request.getParameter("currentRound"));
            PlayerInstance playerInstance = new PlayerInstance(playerId, role, status, gameId, currentRound);

            DAO dao = new DAO();
            /*
            if (dao.getElapsedTime(gameId) >= GameParameters.getRoundDuration) {
                if (dao.getCurrentRound(gameId) != playerInstance.getCurrentRound()) {
                    
                    request.setAttribute("playerInstance", playerInstance);
                    request.getRequestDispatcher("ProcessRound").forward(request, response);
                }

            }*/
            
            //else
            {
                if (selectedPlayer == null) {
                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
            } else {
                if (action.equals("Vote!")) {

                    dao.castVote(gameId, playerId, selectedPlayer);

                }
                if (action.equals("Kill!")) {

                    dao.killOrder(gameId, playerId, selectedPlayer);

                }

                request.setAttribute("playerInstance", playerInstance);
                request.getRequestDispatcher("GoToGamePage").forward(request, response);
            }
            }

            
        }// catch (SQLException ex) {
           // Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    //}

//}
