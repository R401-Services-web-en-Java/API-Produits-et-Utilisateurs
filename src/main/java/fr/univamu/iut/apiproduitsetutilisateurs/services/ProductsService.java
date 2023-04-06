package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class ProductsService {
    protected ProductsRepositoryInterface productsRepo;

    public ProductsService(ProductsRepositoryInterface productsRepo) {
        this.productsRepo = productsRepo;
    }

    public String getAllProducts() {
        ArrayList<Products> allProducts = productsRepo.getAllProducts();

        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProducts);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getProductJSON(String name) {
        String result = null;
        Products myUser = productsRepo.getProduct(name);

        if( myUser != null ) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public void addProduct(Products product) {
        if (productsRepo.getProduct(product.getName()) != null) {
            throw new RuntimeException("Product already exists");
        }
        productsRepo.addProduct(product);
    }

    public void deleteProduct(String name) {
        if (productsRepo.getProduct(name) == null) {
            throw new NotFoundException();
        }
        productsRepo.deleteProduct(name);
    }

    public void updateProduct(Products product) {
        if (productsRepo.getProduct(product.getName()) == null) {
            throw new NotFoundException();
        }
        productsRepo.updateProduct(product);
    }
}
