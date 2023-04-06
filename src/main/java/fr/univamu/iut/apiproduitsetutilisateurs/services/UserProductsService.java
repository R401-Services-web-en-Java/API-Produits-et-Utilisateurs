package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserProductsRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class UserProductsService {
    protected UserProductsRepositoryInterface userProductsRepo;

    public UserProductsService(UserProductsRepositoryInterface userProductsRepo) {
        this.userProductsRepo = userProductsRepo;
    }

    public String getAllUsersJSON(){

        ArrayList<User> allUsers = userProductsRepo.getAllUsers();

        for( User currentUser : allUsers ){
            currentUser.setMail("");
            currentUser.setPassword("");
        }

        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUsers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getUserJSON( String username ){
        String result = null;
        User myUser = userProductsRepo.getUser(username);

        if( myUser != null ) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public void addUser(User user) {
        if (userProductsRepo.getUser(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        userProductsRepo.addUser(user);
    }

    public void deleteUser(String username) {
        if (userProductsRepo.getUser(username) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.deleteUser(username);
    }

    public void updateUser(User user) {
        if (userProductsRepo.getUser(user.getUsername()) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.updateUser(user);
    }

    public String getAllProducts() {
        ArrayList<Products> allProducts = userProductsRepo.getAllProducts();

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
        Products myUser = userProductsRepo.getProduct(name);

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
        if (userProductsRepo.getProduct(product.getName()) != null) {
            throw new RuntimeException("Product already exists");
        }
        userProductsRepo.addProduct(product);
    }

    public void deleteProduct(String name) {
        if (userProductsRepo.getProduct(name) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.deleteProduct(name);
    }

    public void updateProduct(Products product) {
        if (userProductsRepo.getProduct(product.getName()) == null) {
            throw new NotFoundException();
        }
        userProductsRepo.updateProduct(product);
    }
}
