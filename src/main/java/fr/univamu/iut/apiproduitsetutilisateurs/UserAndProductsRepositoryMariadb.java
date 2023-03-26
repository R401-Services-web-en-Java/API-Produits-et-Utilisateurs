package fr.univamu.iut.apiproduitsetutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class UserAndProductsRepositoryMariadb implements UserAndProductsRepositoryInterface, Closeable {
    protected Connection dbConnection ;

    public UserAndProductsRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public User getUser(String username) {
        User selectedUser = null;

        String query = "SELECT * FROM USERS WHERE username=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, username);

            ResultSet result = ps.executeQuery();

            if( result.next() )
            {
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String mail = result.getString("mail");
                String password = result.getString("password");
                String role = result.getString("role");

                selectedUser = new User(username, firstname, lastname, mail, password, role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUsers ;

        String query = "SELECT * FROM USERS";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();
            listUsers = new ArrayList<>();

            while ( result.next() )
            {
                String username = result.getString("username");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String mail = result.getString("mail");
                String password = result.getString("password");
                String role = result.getString("role");

                User currentUser = new User(username, firstname, lastname, mail, password, role);
                listUsers.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }

    @Override
    public void addUser(User user) {
        String query = "INSERT INTO USERS (username, firstname, lastname, mail, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getMail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
