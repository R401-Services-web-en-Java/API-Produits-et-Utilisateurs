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

@Path("/authenticate")
@ApplicationScoped
public class UserAuthenticationResource {

    /**
     * Service utilisé pour accéder aux données des utilisateurs
     */
    private UserAuthenticationService auth;

    public UserAuthenticationResource(){}

    public @Inject UserAuthenticationResource(UserProductsRepositoryInterface userAndProductsRepo ){
        this.auth = new UserAuthenticationService( userAndProductsRepo ) ;
    }

    @GET
    @Produces("text/plain")
    public Response authenticate(@Context ContainerRequestContext requestContext) throws UnsupportedEncodingException {
        boolean res = false;

        // Récupération du header de la requête HTTP et
        // vérification de la présence des informations pour une authentification "basic"
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic")) {
            // envoie d'un code d'erreur avec dans l'en-tête le protocol à utiliser
            return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Basic").build();
        }

        // Récupération et transformation de l'identifiant et du mot de passe encodé en base 64
        String[] tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), "UTF-8")).split(":");
        final String id = tokens[0];
        final String pwd = tokens[1];

        // authentification
        res = auth.isValidUser(id, pwd);

        // envoie d'une réponse avec la valeur de l'authentification
        return Response.ok(String.valueOf(res)).build();
    }
}