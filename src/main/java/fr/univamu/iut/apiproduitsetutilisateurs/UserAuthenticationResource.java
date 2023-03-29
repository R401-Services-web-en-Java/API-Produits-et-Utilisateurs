package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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

    @POST
    @Produces("text/plain")
    public Response authenticate(String body) {
        boolean res = false;

        // Extraction des données d'authentification du corps de la requête POST
        Map<String, String> params = parseParams(body);
        String id = params.get("username");
        String pwd = params.get("password");

        // Vérification que les paramètres sont présents
        if (id == null || pwd == null) {
            // envoie d'un code d'erreur
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // authentification
        res = auth.isValidUser(id, pwd);

        // envoie d'une réponse avec la valeur de l'authentification
        return Response.ok(String.valueOf(res)).build();
    }

    private Map<String, String> parseParams(String body) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                params.put(parts[0], parts[1]);
            }
        }
        return params;
    }
}