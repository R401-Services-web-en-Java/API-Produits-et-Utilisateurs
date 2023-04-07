package fr.univamu.iut.apiproduitsetutilisateurs;

import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryMariadb;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryMariadb;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.ProductsResource;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.UserAuthenticationResource;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.UserResource;
import fr.univamu.iut.apiproduitsetutilisateurs.services.ProductsService;
import fr.univamu.iut.apiproduitsetutilisateurs.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Application class for the User Products API. Configures and provides resources and services for handling
 * user and product operations.
 */
@ApplicationPath("/api")
@ApplicationScoped
public class UserProductsApplication extends Application {

    /**
     * Produces an instance of UserRepositoryInterface for handling user database operations.
     * Creates a new UserRepositoryMariadb instance with the database connection information.
     *
     * @return An instance of UserRepositoryInterface.
     */
    @Produces
    private UserRepositoryInterface openDbUserConnection(){
        UserRepositoryMariadb db = null;

        try{
            db = new UserRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Produces an instance of ProductsRepositoryInterface for handling product database operations.
     * Creates a new ProductsRepositoryMariadb instance with the database connection information.
     *
     * @return An instance of ProductsRepositoryInterface.
     */
    @Produces
    private ProductsRepositoryInterface openDbProductsConnection(){
        ProductsRepositoryMariadb db = null;

        try{
            db = new ProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Closes the user database connection when the UserRepositoryInterface instance is disposed.
     *
     * @param userRepo The UserRepositoryInterface instance to be disposed.
     */
    private void closeDbUserConnection(@Disposes UserRepositoryInterface userRepo ) {
        userRepo.close();
    }

    /**
     * Closes the product database connection when the ProductsRepositoryInterface instance is disposed.
     *
     * @param productsRepo The ProductsRepositoryInterface instance to be disposed.
     */
    private void closeDbProductsConnection(@Disposes ProductsRepositoryInterface productsRepo ) {
        productsRepo.close();
    }

    /**
     * Returns a set of singleton resources to be used in the application.
     * Creates and adds instances of AuthFilter, UserResource, ProductsResource, and UserAuthenticationResource
     * to the set, with appropriate dependencies injected.
     *
     * @return A set of singleton resources.
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        UserRepositoryMariadb dbUser = null;
        try {
            dbUser = new UserRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        ProductsRepositoryMariadb dbProducts = null;
        try {
            dbProducts = new ProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        set.add(new AuthFilter());
        set.add(new UserResource(new UserService(dbUser)));
        set.add(new ProductsResource(new ProductsService(dbProducts)));
        set.add(new UserAuthenticationResource(dbUser));

        return set;
    }
}

