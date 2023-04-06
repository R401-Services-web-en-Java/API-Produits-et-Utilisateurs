package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class UserService {
    protected UserRepositoryInterface userProductsRepo;

    public UserService(UserRepositoryInterface userProductsRepo) {
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
}
