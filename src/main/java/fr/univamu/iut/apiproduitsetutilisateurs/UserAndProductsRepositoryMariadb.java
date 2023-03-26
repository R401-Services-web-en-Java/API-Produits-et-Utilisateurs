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
    public User getUser(String id) {
        User selectedUser = null;

        String query = "SELECT * FROM User WHERE id=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, id);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du user est valide)
            if( result.next() )
            {
                String name = result.getString("name");
                String pwd = result.getString("pwd");
                String mail = result.getString("mail");

                // création et initialisation de l'objet Book
                selectedUser = new User(name, pwd, mail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUsers ;

        String query = "SELECT * FROM User";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listUsers = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                String name = result.getString("name");
                String pwd = result.getString("pwd");
                String mail = result.getString("mail");

                // création du user courant
                User currentUser = new User(name, pwd, mail);

                listUsers.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }
}
