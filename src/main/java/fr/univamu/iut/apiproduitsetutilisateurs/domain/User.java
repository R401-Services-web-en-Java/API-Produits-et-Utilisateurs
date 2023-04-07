package fr.univamu.iut.apiproduitsetutilisateurs.domain;

/**
 * The User class represents a user with their properties such as username, firstname, lastname, mail, password, and role.
 * It provides methods to get and set these properties.
 */
public class User {
    protected String username; // The username of the user
    protected String firstname; // The firstname of the user
    protected String lastname; // The lastname of the user
    protected String mail; // The email of the user
    protected String password; // The password of the user
    protected String role; // The role of the user

    /**
     * Default constructor for User class.
     */
    public User() {}

    /**
     * Constructor for User class with parameters.
     *
     * @param username The username of the user.
     * @param firstname The firstname of the user.
     * @param lastname The lastname of the user.
     * @param mail The email of the user.
     * @param password The password of the user.
     * @param role The role of the user.
     */
    public User(String username, String firstname, String lastname, String mail, String password, String role) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the firstname of the user.
     *
     * @return The firstname of the user.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the firstname of the user.
     *
     * @param firstname The firstname to set.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the lastname of the user.
     *
     * @return The lastname of the user.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the lastname of the user.
     *
     * @param lastname The lastname to set.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the email of the user.
     *
     * @return The email of the user.
     */
    public String getMail() {
        return mail;
    }

    /**
     * Set the email of the user.
     *
     * @param mail The email to set.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the role of the user.
     *
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Set the role of the user.
     *
     * @param role The role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }
}
