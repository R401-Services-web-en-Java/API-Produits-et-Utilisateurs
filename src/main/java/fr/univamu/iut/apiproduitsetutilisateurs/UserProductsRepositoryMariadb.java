package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.ws.rs.NotFoundException;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class UserProductsRepositoryMariadb implements UserProductsRepositoryInterface, Closeable {
    protected Connection dbConnection ;

    public UserProductsRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
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
    public ArrayList<Products> getAllProducts() {
        ArrayList<Products> listProducts ;

        String query = "SELECT * FROM PRODUCTS";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();
            listProducts = new ArrayList<>();

            while ( result.next() )
            {
                String name = result.getString("name");
                int quantity_stock = result.getInt("quantity_stock");
                float price = result.getFloat("price");
                String unit = result.getString("unit");

                Products currentProduct = new Products(name, quantity_stock, price, unit);
                listProducts.add(currentProduct);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProducts;
    }

    @Override
    public Products getProduct(String name) {
        Products selectedProduct = null;

        String query = "SELECT * FROM PRODUCTS WHERE name=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, name);

            ResultSet result = ps.executeQuery();

            if( result.next() )
            {
                int quantity_stock = result.getInt("quantity_stock");
                float price = result.getFloat("price");
                String unit = result.getString("unit");

                selectedProduct = new Products(name, quantity_stock, price, unit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedProduct;
    }

    @Override
    public void addProduct(Products product) {
        String query = "INSERT INTO PRODUCTS (name, quantity_stock, price, unit) VALUES(?, ?, ?, ?)";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity_stock());
            ps.setFloat(3, product.getPrice());
            ps.setString(4, product.getUnit());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateProduct(Products product) {
        String query = "UPDATE PRODUCTS SET name=?, quantity_stock=?, price=?, unit=? WHERE name=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, product.getName());
            ps.setInt(2, product.getQuantity_stock());
            ps.setFloat(3, product.getPrice());
            ps.setString(4, product.getUnit());
            ps.setString(5, product.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProduct(String name) {
        String query = "DELETE FROM PRODUCTS WHERE name=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
