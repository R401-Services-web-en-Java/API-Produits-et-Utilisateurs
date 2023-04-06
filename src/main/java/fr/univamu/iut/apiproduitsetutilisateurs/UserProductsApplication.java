package fr.univamu.iut.apiproduitsetutilisateurs;

import fr.univamu.iut.apiproduitsetutilisateurs.model.UserProductsRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserProductsRepositoryMariadb;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.ProductsResource;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.UserAuthenticationResource;
import fr.univamu.iut.apiproduitsetutilisateurs.ressources.UserResource;
import fr.univamu.iut.apiproduitsetutilisateurs.services.UserProductsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class UserProductsApplication extends Application {

    @Produces
    private UserProductsRepositoryInterface openDbConnection(){
        UserProductsRepositoryMariadb db = null;

        try{
            db = new UserProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes UserProductsRepositoryInterface userAndProductsRepo ) {
        userAndProductsRepo.close();
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        UserProductsRepositoryMariadb db = null;
        try {
            db = new UserProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        set.add(new AuthFilter());
        set.add(new UserResource(new UserProductsService(db)));
        set.add(new ProductsResource(new UserProductsService(db)));
        set.add(new UserAuthenticationResource(db));


        return set;
    }

}

