package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

/**
 * A filter class that implements the ContainerRequestFilter interface to perform authentication and authorization
 * checks on incoming requests.
 */
public class AuthFilter implements ContainerRequestFilter {

    /**
     * Filters the incoming request by checking the Authorization header for a valid Bearer token.
     * If the token is not present or invalid, it throws a NotAuthorizedException.
     *
     * @param requestContext The request context containing the incoming request information.
     * @throws IOException If an I/O error occurs during the filter execution.
     * @throws NotAuthorizedException If the Bearer token is not present or invalid.
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Bearer");
        }

        String token = authHeader.substring("Bearer".length()).trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    /**
     * Validates the Bearer token by checking if it matches a predefined value.
     * If the token is invalid, it throws an Exception with an error message.
     *
     * @param token The Bearer token to be validated.
     * @throws Exception If the token is invalid.
     */
    void validateToken(String token) throws Exception {
        if (!token.equals("token")) {
            throw new Exception("Invalid token");
        }
    }
}