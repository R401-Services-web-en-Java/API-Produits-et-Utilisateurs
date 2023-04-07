package fr.univamu.iut.apiproduitsetutilisateurs.ressources;

import fr.univamu.iut.apiproduitsetutilisateurs.services.UserAuthenticationService;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource class for user authentication.
 * Exposes endpoints for authenticating users using username and password.
 *
 * @Path("/authenticate")
 * @ApplicationScoped
 */
@Path("/authenticate")
@ApplicationScoped
public class UserAuthenticationResource {

    /**
     * Service used to access user data for authentication.
     */
    private UserAuthenticationService auth;

    /**
     * Default constructor.
     */
    public UserAuthenticationResource(){}

    /**
     * Constructor with dependency injection for UserRepositoryInterface.
     *
     * @param userRepo User repository interface implementation
     */
    public @Inject UserAuthenticationResource(UserRepositoryInterface userRepo ){
        this.auth = new UserAuthenticationService(userRepo);
    }

    /**
     * Endpoint for user authentication using POST request.
     * Accepts username and password in request body and returns true if valid, false otherwise.
     *
     * @param body Request body containing username and password as parameters
     * @return Response with authentication result as plain text
     */
    @POST
    @Produces("text/plain")
    public Response authenticate(String body) {
        boolean res = false;

        // Extracting authentication data from POST request body
        Map<String, String> params = parseParams(body);
        String id = params.get("username");
        String pwd = params.get("password");

        // Checking if parameters are present
        if (id == null || pwd == null) {
            // Sending a BAD_REQUEST response
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Authenticating user
        res = auth.isValidUser(id, pwd);

        // Sending response with authentication result
        return Response.ok(String.valueOf(res)).build();
    }

    /**
     * Utility method to parse parameters from request body.
     *
     * @param body Request body as string
     * @return Map of parameter key-value pairs
     */
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
