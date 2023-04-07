package fr.univamu.iut.apiproduitsetutilisateurs.ressources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.services.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

/**
 * RESTful web service endpoint for managing users.
 * This class provides CRUD (Create, Read, Update, Delete) operations for users.
 *
 * @Path("/users") - Specifies the base URI path for this resource.
 * @ApplicationScoped - Specifies that instances of this class are application scoped.
 */
@Path("/users")
@ApplicationScoped
public class UserResource {

    private UserService service;

    /**
     * Default constructor.
     */
    public UserResource(){}

    /**
     * Constructor with dependency injection for UserRepositoryInterface.
     *
     * @param userAndProductsRep - The UserRepositoryInterface implementation to be injected.
     */
    public @Inject UserResource(UserRepositoryInterface userAndProductsRep ){
        this.service = new UserService( userAndProductsRep) ;
    }

    /**
     * Constructor with UserService dependency injection.
     *
     * @param service - The UserService instance to be injected.
     */
    public UserResource( UserService service ){
        this.service = service;
    }

    /**
     * Retrieve all users in JSON format.
     *
     * @return String - The JSON representation of all users.
     */
    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }

    /**
     * Retrieve a user by username in JSON format.
     *
     * @param username - The username of the user to retrieve.
     * @return String - The JSON representation of the retrieved user.
     * @throws NotFoundException - If the user with the specified username is not found.
     */
    @GET
    @Path("{username}")
    @Produces("application/json")
    public String getUser( @PathParam("username") String username){
        String result = service.getUserJSON(username);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Create a new user from JSON input.
     *
     * @param userJson - The JSON representation of the user to create.
     * @return Response - The HTTP response indicating the result of the operation.
     */
    @POST
    @Consumes("application/json")
    public Response createUser(String userJson) {
        try {
            User user = new ObjectMapper().readValue(userJson, User.class);

            service.addUser(user);

            URI location = new URI("/users/" + user.getUsername());
            return Response.created(location).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Delete a user by username.
     *
     * @param username - The username of the user to delete.
     * @return Response - The HTTP response indicating the result of the operation.
     */
    @DELETE
    @Path("{username}")
    public Response deleteUser(@PathParam("username") String username) {
        service.deleteUser(username);
        return Response.noContent().build();
    }

    /**
     * Update an existing user with JSON input.
     *
     * @param username - The username of the user to update.
     * @param userJson - The JSON representation of the updated user.
     * @return Response - The HTTP response indicating the result of the operation.
     */
    @PUT
    @Path("{username}")
    @Consumes("application/json")
    public Response modifyUser(@PathParam("username") String username, String userJson) {
        try {
            User user = new ObjectMapper().readValue(userJson, User.class);
            user.setUsername(username);

            service.updateUser(user);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}

