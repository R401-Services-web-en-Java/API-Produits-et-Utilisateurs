package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationServiceTest {

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @Mock
    private UserRepositoryInterface userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsValidUser_WithValidUsernameAndPassword_ReturnsTrue() {
        String username = "john.doe";
        String password = "mdp";
        User user = new User("john.doe", "John", "Doe","johndoe@gmail.com", "mdp", "user");;
        when(userRepository.getUser(username)).thenReturn(user);
        boolean result = userAuthenticationService.isValidUser(username, password);
        assertTrue(result);
        verify(userRepository, times(1)).getUser(username);
    }

    @Test
    public void testIsValidUser_WithInvalidUsername_ReturnsFalse() {
        String username = "john.doe";
        String password = "mdp";
        when(userRepository.getUser(username)).thenReturn(null);
        boolean result = userAuthenticationService.isValidUser(username, password);
        assertFalse(result);
        verify(userRepository, times(1)).getUser(username);
    }

    @Test
    public void testIsValidUser_WithInvalidPassword_ReturnsFalse() {
        String username = "john.doe";
        String password = "falsePassword";
        User user = new User("john.doe", "John", "Doe","johndoe@gmail.com", "mdp", "user");;
        when(userRepository.getUser(username)).thenReturn(user);
        boolean result = userAuthenticationService.isValidUser(username, password);
        assertFalse(result);
        verify(userRepository, times(1)).getUser(username);
    }

}
