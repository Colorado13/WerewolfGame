package ca.werewolfgame.dao;

import ca.werewolfgame.beans.*;
import ca.werewolfgame.logic.*;
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

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO mainchat VALUES (?,?,NOW())";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setString(1, message.getUsername());
            ps.setString(2, message.getMessage());
            //ps.setString(3, currentTime);

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

            String preparedStatement = "INSERT INTO gamechat VALUES (?,?,?,NOW())";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, gameId);
            ps.setString(2, message.getUsername());
            ps.setString(3, message.getMessage());
            //ps.setString(4, currentTime);

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
                String preparedStatement = "INSERT INTO werewolfchat VALUES (?,?,?,NOW())";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, gameId);
                ps.setString(2, message.getUsername());
                ps.setString(3, message.getMessage());
                //ps.setString(4, currentTime);
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
        int chatHistorySize = GameParameters.mainChatHistorySize;
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
        int chatHistorySize = GameParameters.gameChatHistorySize;
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
        int chatHistorySize = GameParameters.wwChatHistorySize;
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
        int chatHistorySize = GameParameters.deadChatHistorySize;
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

    public void createGame(int gameId, String[] players, HashMap<String, String> gameSetup) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);
            for (int i = 0; i < gameSetup.size(); i++) {
                String preparedStatement = "INSERT INTO gameid (gameid, playerid, role) VALUES (?,?,?)";
                PreparedStatement ps = con.prepareStatement(preparedStatement);
                ps.setInt(1, gameId);
                ps.setString(2, players[i]);
                ps.setString(3, gameSetup.get(players[i]));
                ps.executeUpdate();

            }
            //Add an initial "Game Started" message
            java.util.Date dt = new java.util.Date();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String currentTime = sdf.format(dt);

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO gamechat VALUES (?,?,?,NOW())";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, gameId);
            ps.setString(2, "SYSTEM");
            ps.setString(3, "GAME STARTED!");
            //ps.setString(4, currentTime);

            ps.executeUpdate();

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

        String query = "SELECT playerid FROM gameid WHERE gameid = " + gameId + " AND status LIKE 'ALIVE'";

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

        String query = "SELECT playerid FROM gameid WHERE gameid = " + gameId + " AND status LIKE 'ALIVE' AND role NOT LIKE 'werewolf'";

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

    public int getCurrentRound(int gameId) throws SQLException {
        int currentRound;

        String query = "SELECT MAX(gameround) from games WHERE gameid like '" + gameId + "'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();
            currentRound = Integer.parseInt(rs.getString(1));
            con.close();

        }
        return currentRound;
    }

    public int getVoteIndex(int gameId) throws SQLException {
        int currentIndex;

        DAO dao = new DAO();
        int currentRound = dao.getCurrentRound(gameId);

        String query = "SELECT MAX(voteindex) from votes WHERE gameid like '" + gameId + "' AND gameround = " + currentRound;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();

            if (rs.getString(1) == null) {
                currentIndex = 0;
            } else {
                currentIndex = Integer.parseInt(rs.getString(1));
            }

            //System.out.println(currentIndex);
            con.close();

        }
        return currentIndex;
    }

    public void castVote(int gameId, String playerId, String selectedPlayer) {
        try {
            DAO dao = new DAO();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO votes VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, gameId);
            ps.setString(2, playerId);
            ps.setString(3, selectedPlayer);
            ps.setInt(4, dao.getCurrentRound(gameId));
            ps.setInt(5, dao.getVoteIndex(gameId) + 1);

            ps.executeUpdate();

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public void killOrder(int gameId, String playerId, String selectedPlayer) {
        try {
            DAO dao = new DAO();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con;

            con = DriverManager.getConnection(host + database, username, password);

            String preparedStatement = "INSERT INTO killorder VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(preparedStatement);
            ps.setInt(1, gameId);
            ps.setString(2, playerId);
            ps.setString(3, selectedPlayer);
            ps.setInt(4, dao.getCurrentRound(gameId));
            ps.setInt(5, dao.getOrderIndex(gameId) + 1);

            ps.executeUpdate();

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public int getOrderIndex(int gameId) throws SQLException {
        int currentIndex;

        DAO dao = new DAO();
        int currentRound = dao.getCurrentRound(gameId);

        String query = "SELECT MAX(orderindex) from killorder WHERE gameid like '" + gameId + "' AND gameround = " + currentRound;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();
            if (rs.getString(1) == null) {
                currentIndex = 0;
            } else {
                currentIndex = Integer.parseInt(rs.getString(1));
            }

            //System.out.println(currentIndex);
            con.close();

        }
        return currentIndex;
    }

    public Timestamp getStartTime(int gameId) throws SQLException {

        java.sql.Timestamp startTime;

        String query = "SELECT MAX(thetime) FROM gamechat WHERE gameid = "
                + gameId
                + " AND playerid LIKE 'SYSTEM'";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();

            startTime = rs.getTimestamp(1);

            con.close();

        }

        return startTime;

    }

    public long getElapsedTime(int gameId) throws SQLException {

        DAO dao = new DAO();

        java.sql.Timestamp startTime = dao.getStartTime(gameId);
        java.sql.Timestamp currentTime;

        String query = "SELECT NOW()";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();

            currentTime = rs.getTimestamp(1);

            con.close();

        }
        long milliseconds1 = startTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;

        //long diffMinutes = diff / (60 * 1000);
        //long diffHours = diff / (60 * 60 * 1000);
        //long diffDays = diff / (24 * 60 * 60 * 1000);
        /*
        System.out.println("Seconds:" + diffSeconds);
        System.out.println("Minutes:" + diffMinutes);
        System.out.println("Hours:" + diffHours);
        System.out.println("Days:" + diffDays);
         */
        return diffSeconds;
    }

    public void lynchPlayer(int gameId, int currentRound) {

    }

    public void killPlayer(int gameId, int currentRound) throws SQLException, ClassNotFoundException {
        Message message;
        String query = "SELECT victimid FROM killorder WHERE gameid = '"
                + gameId
                + "' AND orderindex = (SELECT MAX(orderindex) FROM killorder WHERE gameid = '"
                + gameId
                + "' AND gameround = '"
                + currentRound
                + "')";

        System.out.println(query);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String playerId = (rs.getString(1)) + " WAS KILLED DURING THE NIGHT!";
            message = new Message("SYSTEM", playerId);
            AddMessage(message, gameId);

            changePlayerToDead(gameId, playerId);
            con.close();
        }

    }

    public void checkEndGame() {

    }

    public void increaseRound(int gameId) throws SQLException, ClassNotFoundException {
        int currentRound = getCurrentRound(gameId);
        int newRound = currentRound + 1;

        Class.forName("com.mysql.jdbc.Driver");
        Connection con;

        con = DriverManager.getConnection(host + database, username, password);

        String preparedStatement = "UPDATE games SET gameround = "
                + newRound
                + " WHERE gameId = "
                + gameId;

        System.out.println(preparedStatement);

        PreparedStatement ps = con.prepareStatement(preparedStatement);

        ps.executeUpdate();
        con.close();

    }

    private void changePlayerToDead(int gameId, String playerId) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection con;

        con = DriverManager.getConnection(host + database, username, password);

        String preparedStatement = "UPDATE gameid SET status = 'DEAD' WHERE gameid = "
                + gameId
                + " AND WHERE playerid = "
                + playerId;

        System.out.println(preparedStatement);

        PreparedStatement ps = con.prepareStatement(preparedStatement);

        ps.executeUpdate();
        con.close();
    }
    public ArrayList<Vote> getVotesAgainst(int gameId, String playerId, int currentRound) throws SQLException
    {
        ArrayList<Vote> votes = new ArrayList<Vote>();
        
        String query = "SELECT * FROM votes WHERE gameid = " + gameId + " AND gameround  = " + currentRound + " AND votedid LIKE '" + playerId + "'";
        Vote vote = new Vote();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                vote.setGameId(rs.getInt(1));
                vote.setVotingId(rs.getString(2));
                vote.setVotedId(rs.getString(3));
                vote.setRound(rs.getInt(4));
                vote.setVoteIndex(rs.getInt(5));
                votes.add(vote);
            }
            con.close();
        }     
        
        return votes;
    }
    
    public ArrayList<Vote> getVotesBy(int gameId, String playerId, int currentRound) throws SQLException
    {
        ArrayList<Vote> votes = new ArrayList<Vote>();
        
        String query = "SELECT * FROM votes WHERE gameid = " + gameId + " AND gameround  = " + currentRound + " AND votingid LIKE '" + playerId + "'";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = DriverManager.getConnection(host + database, username, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Vote vote = new Vote();
                vote.setGameId(rs.getInt(1));
                vote.setVotingId(rs.getString(2));
                vote.setVotedId(rs.getString(3));
                vote.setRound(rs.getInt(4));
                vote.setVoteIndex(rs.getInt(5));
                votes.add(vote);
            }
            con.close();
        }     
        
        return votes;
    }
}
