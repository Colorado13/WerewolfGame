package ca.werewolfgame.controllers;

import ca.werewolfgame.dao.*;
import ca.werewolfgame.logic.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateGameServlet", urlPatterns = {"/CreateGameServlet"})
public class CreateGameServlet extends HttpServlet {

   

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String[] players = request.getParameterValues("player");
            
            int playerCount = players.length;
            
            DAO dao = new DAO();
            
            int gameID = dao.createGameID(playerCount);
            System.out.println(gameID);
            HashMap<String, String> gameSetup = gameCreator.setupGame(players, playerCount);
            dao.createGame(gameID, players, gameSetup);
            
            
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CreateGameServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }

    

}
