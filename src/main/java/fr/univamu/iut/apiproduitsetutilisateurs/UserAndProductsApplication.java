package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class UserAndProductsApplication extends Application {

    @Produces
    private UserAndProductsRepositoryInterface openDbConnection(){
        UserAndProductsRepositoryMariadb db = null;

        try{
            db = new UserAndProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes UserAndProductsRepositoryInterface userAndProductsRepo ) {
        userAndProductsRepo.close();
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        UserAndProductsRepositoryMariadb db = null;
        try {
            db = new UserAndProductsRepositoryMariadb("jdbc:mariadb://mysql-fred.alwaysdata.net/fred_api_users_products", "fred_api_project", "Frederic13!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        set.add(new UserResource(new UserAndProductsService(db)));
        set.add(new UserAuthenticationResource(db));
        //set.add(new AuthFilter());

        return set;
    }

}

