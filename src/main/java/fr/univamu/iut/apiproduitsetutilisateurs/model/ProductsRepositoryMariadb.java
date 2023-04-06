package fr.univamu.iut.apiproduitsetutilisateurs.model;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;

import java.sql.*;
import java.util.ArrayList;

public class ProductsRepositoryMariadb implements ProductsRepositoryInterface {
    protected Connection dbConnection ;

    public ProductsRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
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
}
