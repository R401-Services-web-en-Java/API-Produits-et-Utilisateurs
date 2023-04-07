package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import jakarta.ws.rs.NotFoundException;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of UserRepositoryInterface that interacts with user data stored in MariaDB.
 * Provides methods to perform CRUD (Create, Read, Update, Delete) operations on user data.
 * Implements Closeable interface to enable closing of the database connection.
 */
public class UserRepositoryMariadb implements UserRepositoryInterface, Closeable {
    /**
     * The connection to the database.
     */
    protected Connection dbConnection ;

    /**
     * Constructs a UserRepositoryMariadb object with the provided database connection information.
     *
     * @param infoConnection The connection URL for MariaDB.
     * @param user The username to connect to the database.
     * @param pwd The password to connect to the database.
     * @throws SQLException If an SQL exception occurs while establishing the database connection.
     * @throws ClassNotFoundException If the MariaDB JDBC driver class is not found.
     */
    public UserRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    /**
     * Closes the database connection.
     * Overrides the close() method from Closeable interface.
     */
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the given username, or null if not found.
     */
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

    /**
     * Retrieves all users.
     *
     * @return An ArrayList of all users.
     */
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

    /**
     * Adds a user to the database.
     *
     * @param user The user to add.
     */
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

    /**
     * Deletes a user from the database by username.
     *
     * @param username The username of the user to delete.
     */
    @Override
    public void deleteUser(String username) {
        String query = "DELETE FROM USERS WHERE username = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a user in the database.
     *
     * @param user The updated user object.
     * @throws NotFoundException If the user with the given username is not found in the database.
     */
    @Override
    public void updateUser(User user) {
        String query = "UPDATE USERS SET firstname = ?, lastname = ?, mail = ?, password = ?, role = ? WHERE username = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getMail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getUsername());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new NotFoundException("User with username " + user.getUsername() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
