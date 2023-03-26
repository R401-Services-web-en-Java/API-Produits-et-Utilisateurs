package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Ressource associée à l'authentification des utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/authenticate")
@ApplicationScoped
public class UserAuthenticationResource {

    private UserAuthenticationService auth;

    public UserAuthenticationResource(){}

    public @Inject UserAuthenticationResource( UserAndProductsRepositoryInterface userRepo ){
        this.auth = new UserAuthenticationService( userRepo ) ;
    }

    @GET
    @Produces("text/plain")
    public Response authenticate(@Context ContainerRequestContext requestContext) throws UnsupportedEncodingException {
        boolean res = false;

        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic")) {
            return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();
        }

        String[] tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), "UTF-8")).split(":");
        final String username = tokens[0];
        final String password = tokens[1];

        res = auth.isValidUser(username, password);

        return Response.ok(String.valueOf(res)).build();
    }
}
