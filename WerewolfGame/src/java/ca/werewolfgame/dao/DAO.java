package ca.werewolfgame.dao;

/**
 *
 * @author
 */
import ca.werewolfgame.beans.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    public String host = "jdbc:mysql://localhost/";
    public String username = "root";
    public String password = "root";
    public String database = "java3final";

    public void AddUser(User user) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String name = user.getUsername();
            String userPassword = user.getPassword();
            String privilege = "MEMBER";

            String preparedStatement = "INSERT INTO users VALUES (?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(preparedStatement);

            ps.setString(1, name);
            ps.setString(2, userPassword);
            ps.setString(3, privilege);
            ps.setInt(4, 0);
            ps.setInt(5, 0);

            ps.executeUpdate();

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<User> GetUser() throws SQLException {

        ArrayList<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM users";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));
                user.setPrivilege(rs.getString(3));
                user.setMatches(Integer.parseInt(rs.getString(4)));
                user.setWins(Integer.parseInt(rs.getString(5)));

                usersList.add(user);
            }
        }
        return usersList;
    }

    public void AddMessage(Message message) {
        try {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO mainchat VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setString(1, message.getUsername());
            ps.setString(2, message.getMessage());
            ps.setString(3, currentTime);
            
            ps.executeUpdate();
            
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void AddMessage(Message message, Game game) {
        try {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO " + game.getGameID() + "_chat VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, game.getGameID());
            ps.setString(2, message.getUsername());
            ps.setString(3, message.getMessage());
            ps.setString(4, currentTime);

            ps.executeUpdate();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void AddMessage(Message message, Game game, String chat) {
        try {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            if (chat.equals("werewolf")) {
                String preparedStatement = "INSERT INTO " + game.getGameID() + "_chat_werewolf VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, game.getGameID());
                ps.setString(2, message.getUsername());
                ps.setString(3, message.getMessage());
                ps.setString(4, currentTime);
                ps.executeUpdate();
            }
            else if (chat.equals("dead"))
            {
                String preparedStatement = "INSERT INTO " + game.getGameID() + "_chat_dead VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, game.getGameID());
                ps.setString(2, message.getUsername());
                ps.setString(3, message.getMessage());
                ps.setString(4, currentTime);
                ps.executeUpdate();
                
            }
            
            
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Message> getMainChat() throws SQLException {
        //maybe have a variable somewhere that sets this (web.xml?)
        int chatHistorySize = 10;
        ArrayList<Message> chatHistory = new ArrayList<>();

        String query = "SELECT username, message FROM mainchat order by thetime desc limit " + chatHistorySize;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Message message = new Message();
                message.setUsername(rs.getString(1));
                message.setMessage(rs.getString(2));

                chatHistory.add(message);
            }
        }
        Collections.reverse(chatHistory);
        return chatHistory;
    }

    public ArrayList<Message> getGameChat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Message> getWwChat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Message> getDeadChat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
