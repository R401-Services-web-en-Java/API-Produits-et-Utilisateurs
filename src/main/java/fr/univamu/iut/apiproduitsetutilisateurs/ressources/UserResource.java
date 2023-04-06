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

@Path("/users")
@ApplicationScoped
public class UserResource {

    private UserService service;

    public UserResource(){}

    public @Inject UserResource(UserRepositoryInterface userAndProductsRep ){
        this.service = new UserService( userAndProductsRep) ;
    }

    public UserResource( UserService service ){
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }

    @GET
    @Path("{username}")
    @Produces("application/json")
    public String getUser( @PathParam("username") String username){
        String result = service.getUserJSON(username);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

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

    @DELETE
    @Path("{username}")
    public Response deleteUser(@PathParam("username") String username) {
        service.deleteUser(username);
        return Response.noContent().build();
    }

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

