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

/**
 * Represents a RESTful API resource for managing products.
 *
 * This class provides endpoints for performing CRUD operations (Create, Read, Update, Delete) on products.
 *
 * @Path("/products") - Specifies the base URI path for this resource.
 * @ApplicationScoped - Specifies that instances of this class are application scoped.
 */
@Path("/products")
@ApplicationScoped
public class ProductsResource {

    /**
     * Service used to access products data
     */
    private ProductsService service;

    /**
     * Default constructor for the ProductsResource class.
     */
    public ProductsResource(){}

    /**
     * Constructor for the ProductsResource class that injects the dependency for ProductsRepositoryInterface.
     *
     * @param productsRepo The implementation of ProductsRepositoryInterface to be injected.
     */
    public @Inject ProductsResource(ProductsRepositoryInterface productsRepo ){
        this.service = new ProductsService(productsRepo) ;
    }

    /**
     * Constructor for the ProductsResource class that takes an instance of ProductsService.
     *
     * @param service The ProductsService instance to be used.
     */
    public ProductsResource(ProductsService service){
        this.service = service;
    }

    /**
     * Endpoint for getting all products.
     *
     * This endpoint returns a JSON representation of all the products.
     *
     * @return A JSON string representing all the products.
     *
     * @GET
     * @Produces("application/json")
     */
    @GET
    @Produces("application/json")
    public String getAllProducts() {
        return service.getAllProducts();
    }

    /**
     * Endpoint for getting a product by name.
     *
     * This endpoint returns a JSON representation of a product with the given name.
     *
     * @param name The name of the product to retrieve.
     * @return A JSON string representing the product.
     * @throws NotFoundException If the product with the given name is not found.
     *
     * @GET
     * @Path("{name}")
     * @Produces("application/json")
     */
    @GET
    @Path("{name}")
    @Produces("application/json")
    public String getProduct( @PathParam("name") String name){
        String result = service.getProductJSON(name);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint for creating a new product.
     *
     * This endpoint creates a new product with the given name and price.
     *
     * @param productJson A JSON string representing the product to create.
     * @return A Response object with a status code of 201 (Created) if the product was created successfully.
     * @throws BadRequestException If the product could not be created.
     *
     * @POST
     * @Consumes("application/json")
     */
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

    /**
     * Endpoint for deleting a product.
     *
     * This endpoint deletes the product with the given name.
     *
     * @param name The name of the product to delete.
     * @return A Response object with a status code of 204 (No Content) if the product was deleted successfully.
     * @throws NotFoundException If the product with the given name is not found.
     *
     * @DELETE
     * @Path("{name}")
     */
    @DELETE
    @Path("{name}")
    public Response deleteProduct(@PathParam("name") String name) {
        service.deleteProduct(name);
        return Response.noContent().build();
    }

    /**
     * Endpoint for updating a product.
     *
     * This endpoint updates the product with the given name.
     *
     * @param name The name of the product to update.
     * @param productJson A JSON string representing the product to update.
     * @return A Response object with a status code of 200 (OK) if the product was updated successfully.
     * @throws BadRequestException If the product could not be updated.
     *
     * @PUT
     * @Path("{name}")
     * @Consumes("application/json")
     */
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


