package fr.univamu.iut.apiproduitsetutilisateurs.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("john.doe", "John", "Doe", "john.doe@example.com", "password123", "user");
    }

    @Test
    public void testGetUsername() {
        assertEquals("john.doe", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("jane.doe");
        assertEquals("jane.doe", user.getUsername());
    }

    @Test
    public void testGetFirstname() {
        assertEquals("John", user.getFirstname());
    }

    @Test
    public void testSetFirstname() {
        user.setFirstname("Jane");
        assertEquals("Jane", user.getFirstname());
    }

    @Test
    public void testGetLastname() {
        assertEquals("Doe", user.getLastname());
    }

    @Test
    public void testSetLastname() {
        user.setLastname("Smith");
        assertEquals("Smith", user.getLastname());
    }

    @Test
    public void testGetMail() {
        assertEquals("john.doe@example.com", user.getMail());
    }

    @Test
    public void testSetMail() {
        user.setMail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", user.getMail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testGetRole() {
        assertEquals("user", user.getRole());
    }

    @Test
    public void testSetRole() {
        user.setRole("admin");
        assertEquals("admin", user.getRole());
    }
}
