package fr.univamu.iut.apiproduitsetutilisateurs;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.UriInfo;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class AuthFilterTest {

    private AuthFilter authFilter;
    private ContainerRequestContext requestContext;

    @Before
    public void setUp() {
        authFilter = new AuthFilter();
        requestContext = mock(ContainerRequestContext.class);
        UriInfo uriInfo = mock(UriInfo.class);
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
    }

    @Test
    public void testFilter_WithValidBearerToken_DoesNotThrowException() throws Exception {
        when(requestContext.getHeaderString("Authorization")).thenReturn("Bearer token");
        authFilter.filter(requestContext);
    }

    @Test
    public void testValidateToken_WithValidToken_DoesNotThrowException() throws Exception {
        String token = "token";
        authFilter.validateToken(token);
    }

    @Test(expected = Exception.class)
    public void testValidateToken_WithInvalidToken_ThrowsException() throws Exception {
        String token = "invalid_token";
        authFilter.validateToken(token);
    }
}

