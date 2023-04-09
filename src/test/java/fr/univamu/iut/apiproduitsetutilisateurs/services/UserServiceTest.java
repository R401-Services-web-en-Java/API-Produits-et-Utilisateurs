package fr.univamu.iut.apiproduitsetutilisateurs.services;

import fr.univamu.iut.apiproduitsetutilisateurs.domain.User;
import fr.univamu.iut.apiproduitsetutilisateurs.model.UserRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;
    private UserRepositoryInterface userRepository;

    @Before
    public void setUp() {
        userRepository = mock(UserRepositoryInterface.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void testAddUser() {
        User user = new User("john.doe", "John", "Doe","johndoe@gmail.com", "mdp", "user");
        Mockito.when(userRepository.getUser("john.doe")).thenReturn(null);
        userService.addUser(user);
        Mockito.verify(userRepository).getUser("john.doe");
        Mockito.verify(userRepository).addUser(user);
    }

    @Test
    public void testDeleteUser() {
        Mockito.when(userRepository.getUser("john.doe")).thenReturn(new User("john.doe", "John", "Doe","johndoe@gmail.com", "mdp", "user"));
        userService.deleteUser("john.doe");
        Mockito.verify(userRepository).getUser("john.doe");
        Mockito.verify(userRepository).deleteUser("john.doe");
    }
}