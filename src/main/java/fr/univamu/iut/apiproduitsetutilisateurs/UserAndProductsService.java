package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class UserAndProductsService {
    protected UserAndProductsRepositoryInterface userAndProductsRepo;

    public UserAndProductsService(UserAndProductsRepositoryInterface userAndProductsRepo) {
        this.userAndProductsRepo = userAndProductsRepo;
    }

    public String getAllUsersJSON(){

        ArrayList<User> allUsers = userAndProductsRepo.getAllUsers();

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
        User myUser = userAndProductsRepo.getUser(username);

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
        if (userAndProductsRepo.getUser(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        userAndProductsRepo.addUser(user);
    }

    public void deleteUser(String username) {
        if (userAndProductsRepo.getUser(username) == null) {
            throw new NotFoundException();
        }
        userAndProductsRepo.deleteUser(username);
    }

    public void modifyUser(User user) {
        if (userAndProductsRepo.getUser(user.getUsername()) == null) {
            throw new NotFoundException();
        }
        userAndProductsRepo.modifyUser(user);
    }
}
