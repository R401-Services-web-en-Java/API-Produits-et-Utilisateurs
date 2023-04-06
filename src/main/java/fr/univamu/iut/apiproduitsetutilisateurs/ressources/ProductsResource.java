package fr.univamu.iut.apiproduitsetutilisateurs.ressources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.apiproduitsetutilisateurs.domain.Products;
import fr.univamu.iut.apiproduitsetutilisateurs.model.ProductsRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import fr.univamu.iut.apiproduitsetutilisateurs.services.ProductsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/products")
@ApplicationScoped
public class ProductsResource {

    private ProductsService service;

    public ProductsResource(){}

    public @Inject ProductsResource(ProductsRepositoryInterface productsRepo ){
        this.service = new ProductsService(productsRepo) ;
    }

    public ProductsResource(ProductsService service){
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllProducts() {
        return service.getAllProducts();
    }

    @GET
    @Path("{name}")
    @Produces("application/json")
    public String getProduct( @PathParam("name") String name){
        String result = service.getProductJSON(name);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @POST
    @Consumes("application/json")
    public Response createProduct(String productJson) {
        try {
            Products products = new ObjectMapper().readValue(productJson, Products.class);

            service.addProduct(products);

            URI location = new URI("/products/" + products.getName());
            return Response.created(location).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{name}")
    public Response deleteProduct(@PathParam("name") String name) {
        service.deleteProduct(name);
        return Response.noContent().build();
    }

    @PUT
    @Path("{name}")
    @Consumes("application/json")
    public Response updateProduct(@PathParam("name") String name, String productJson) {
        try {
            Products product = new ObjectMapper().readValue(productJson, Products.class);
            product.setName(name);

            service.updateProduct(product);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}


