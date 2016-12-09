package ca.werewolfgame.dao;

/**
 *
 * @author
 */
import ca.werewolfgame.beans.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
            con.close();
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

    public void AddMessage(Message message, int gameId) {
        try {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO gamechat VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, gameId);
            ps.setString(2, message.getUsername());
            ps.setString(3, message.getMessage());
            ps.setString(4, currentTime);

            ps.executeUpdate();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void AddMessage(Message message, int gameId, String chat) {
        try {

            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            if (chat.equals("werewolf")) {
                String preparedStatement = "INSERT INTO werewolfchat VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, gameId);
                ps.setString(2, message.getUsername());
                ps.setString(3, message.getMessage());
                ps.setString(4, currentTime);
                ps.executeUpdate();

            } else if (chat.equals("deadChat")) {
                String preparedStatement = "INSERT INTO deadchat VALUES (?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, gameId);
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
            con.close();
        }
        Collections.reverse(chatHistory);

        return chatHistory;
    }

    public ArrayList<Message> getGameChat(int gameId) throws SQLException {
        int chatHistorySize = 10;
        ArrayList<Message> chatHistory = new ArrayList<>();

        String query = "SELECT playerid, message FROM gamechat WHERE gameid = '" + gameId + "' order by thetime desc limit " + chatHistorySize;

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
            con.close();
        }
        Collections.reverse(chatHistory);
        return chatHistory;
    }

    public ArrayList<Message> getWwChat(int gameId) throws SQLException {
        int chatHistorySize = 10;
        ArrayList<Message> wwChatHistory = new ArrayList<>();

        String query = "SELECT playerid, message FROM werewolfchat WHERE gameid = '" + gameId + "' order by thetime desc limit " + chatHistorySize;

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

                wwChatHistory.add(message);
            }
            con.close();
        }
        Collections.reverse(wwChatHistory);
        return wwChatHistory;
    }

    public ArrayList<Message> getDeadChat(int gameId) throws SQLException {
        int chatHistorySize = 10;
        ArrayList<Message> deadChatHistory = new ArrayList<>();

        String query = "SELECT playerid, message FROM deadchat WHERE gameid = '" + gameId + "' order by thetime desc limit " + chatHistorySize;

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

                deadChatHistory.add(message);
            }
            con.close();
        }
        Collections.reverse(deadChatHistory);
        return deadChatHistory;
    }

    public int createGameID(int playerCount) throws SQLException, ClassNotFoundException {
        int gameID = 0;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO games (numofplayers) VALUES (?)";

            PreparedStatement ps = con.prepareStatement(preparedStatement);

            ps.setInt(1, playerCount);
            ps.executeUpdate();

            String query = "SELECT MAX(gameID) FROM games";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            gameID = Integer.parseInt((rs.getString(1)));

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

        return gameID;
    }

    public void createGame(int gameID, String[] players, HashMap<String, String> gameSetup) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);
            for (int i = 0; i < gameSetup.size(); i++) {
                String preparedStatement = "INSERT INTO gameid (gameid, playerid, role) VALUES (?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, gameID);
                ps.setString(2, players[i]);
                ps.setString(3, gameSetup.get(players[i]));
                ps.executeUpdate();

            }

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> getPlayers() throws SQLException {

        ArrayList<String> userRoster = new ArrayList<>();

        String query = "SELECT username FROM users";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                userRoster.add(rs.getString(1));
            }
            con.close();
        }
        Collections.sort(userRoster);
        return userRoster;
    }
    
    public ArrayList<String> getPlayers(int gameId) throws SQLException {

        ArrayList<String> userRoster = new ArrayList<>();

        String query = "SELECT playerid FROM gameid WHERE gameid = " + gameId + " AND status LIKE 'ALIVE'" ;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                userRoster.add(rs.getString(1));
            }
            con.close();
        }
        Collections.sort(userRoster);
        return userRoster;
    }
    
    public ArrayList<String> getPlayers(int gameId, String villagers) throws SQLException {

        ArrayList<String> userRoster = new ArrayList<>();

        String query = "SELECT playerid FROM gameid WHERE gameid = " + gameId + " AND status LIKE 'ALIVE' AND role NOT LIKE 'werewolf'" ;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                userRoster.add(rs.getString(1));
            }
            con.close();
        }
        Collections.sort(userRoster);
        return userRoster;
    }

    public ArrayList<Integer> getMyGames(String playerId) throws SQLException {

        ArrayList<Integer> myGames = new ArrayList<>();

        String query = "SELECT gameid from gameid WHERE playerid like '" + playerId + "'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            int i = 0;

            while (rs.next()) {
                myGames.add(Integer.parseInt(rs.getString(1)));

            }
            con.close();
        }

        return myGames;
    }

    public String getRole(int gameId, String playerId) throws SQLException {
        String role;

        String query = "SELECT role from gameid WHERE playerid like '" + playerId + "' AND gameid LIKE '" + gameId + "'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();
            role = rs.getString(1);
            con.close();

        }
        return role;
    }

    public String getStatus(int gameId, String playerId) throws SQLException {
        String status;

        String query = "SELECT status from gameid WHERE playerid like '" + playerId + "' AND gameid LIKE '" + gameId + "'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();
            status = rs.getString(1);
            con.close();

        }
        return status;
    }
}
