package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private UserRepositoryMariadb userRepo;
    private Connection dbConnection;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        BasicConfigurator.configure();
        String infoConnection = "jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products_test";
        String user = "fred_api_project";
        String pwd = "Frederic13!";
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
        userRepo = new UserRepositoryMariadb(infoConnection, user, pwd);
    }

    @After
    public void tearDown() throws SQLException {
        userRepo.close();
        dbConnection.close();
    }

    @Test
    public void testGetAllUsers() {
        ArrayList<User> usersList = userRepo.getAllUsers();
        assertFalse(usersList.isEmpty());
    }

    @Test
    public void testGetUser() {
        String userName = "john_doe";
        User user = userRepo.getUser(userName);
        assertEquals(userName, user.getUsername());
    }

    @Test
    public void testAddUser() {
        User user = new User("justine12", "Justine", "Doma", "justine.doma@gmail.com", "juju", "user");
        userRepo.addUser(user);
        User addedUser = userRepo.getUser(user.getUsername());
        assertNotNull(addedUser);
        assertEquals(user.getUsername(), addedUser.getUsername());
        userRepo.deleteUser(user.getUsername());
    }

    @Test
    public void testUpdateUser() {
        String username = "john_doe";
        User user = userRepo.getUser(username);
        assertNotNull(user);

        user.setLastname("Egenscheviller");
        userRepo.updateUser(user);

        User updatedUser = userRepo.getUser(user.getUsername());
        assertNotNull(updatedUser);
        assertEquals(user.getUsername(), updatedUser.getUsername());

        user.setLastname("Doe"); // reset for next test
        userRepo.updateUser(user);
    }

    @Test
    public void testDeleteUser() {
        String username = "fredvarga33";
        User userAdded = new User("fredvarga33", "Frederic", "EGEN", "fredegen@gmail.com", "eqfEFG", "user");
        userRepo.addUser(userAdded); // add user to delete it after
        User user = userRepo.getUser(username);
        assertNotNull(user);
        userRepo.deleteUser(username);

        User deletedUser = userRepo.getUser(username);
        assertNull(deletedUser);
    }
}
