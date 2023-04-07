package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of ProductsRepositoryInterface that interacts with products data stored in MariaDB.
 * Provides methods to perform CRUD (Create, Read, Update, Delete) operations on products data.
 * Implements Closeable interface to enable closing of the database connection.
 */
public class ProductsRepositoryMariadb implements ProductsRepositoryInterface, Closeable {
    /**
     * The connection to the database.
     */
    protected Connection dbConnection ;

    /**
     * Constructor for ProductsRepositoryMariadb.
     *
     * @param infoConnection The connection information for the MariaDB database.
     * @param user The username for connecting to the database.
     * @param pwd The password for connecting to the database.
     * @throws SQLException If an error occurs while connecting to the database.
     * @throws ClassNotFoundException If the MariaDB JDBC driver class is not found.
     */
    public ProductsRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    /**
     * Closes the connection to the database.
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
     * Get all products from the database.
     *
     * @return An ArrayList of Products containing all products in the database.
     */
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

    /**
     * Get a product from the database by its name.
     *
     * @param name The name of the product to retrieve.
     * @return The Product object corresponding to the given name, or null if not found.
     */
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

    /**
     * Add a product to the database.
     *
     * @param product The Product object to add.
     */
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

    /**
     * Update a product in the database.
     *
     * @param product The Product object to update.
     */
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

    /**
     * Delete a product from the database.
     *
     * @param name The name of the product to delete.
     */
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
}
