package ca.werewolfgame.dao;

/**
 *
 * @author
 */
import ca.werewolfgame.beans.*;
import java.sql.*;
import java.util.ArrayList;
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
}
