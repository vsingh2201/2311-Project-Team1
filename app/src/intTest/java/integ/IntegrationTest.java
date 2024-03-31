package integ;

import utils.DatabaseSeedingUtil;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import models.User;
import repositories.userRepository.UserRepository;

public class IntegrationTest {
    private static UserRepository userRepository;
    
    @BeforeAll
    public static void setUp() {
        DatabaseSeedingUtil db = new DatabaseSeedingUtil();
        db.seedDatabase();
        userRepository = new UserRepository();
    }
    
    @Test
    public void testGetUserById() {
        User user = userRepository.getUserById(1);
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }
    
    @Test
    public void testGetUserByUsername() {
        User user = userRepository.getUserByUsername("janedoe");
        assertNotNull(user);
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("janedoe", user.getUsername());
        assertEquals("password456", user.getPassword());
    }
    
    @Test
    public void testValidateUser() {
        User validUser = userRepository.validateUser("jimbeam", "password789");
        assertNotNull(validUser);
        assertEquals("Jim", validUser.getFirstName());
        assertEquals("Beam", validUser.getLastName());
        assertEquals("jimbeam", validUser.getUsername());
        assertEquals("password789", validUser.getPassword());

        User invalidUser = userRepository.validateUser("jimbeam", "wrongpassword");
        assertNull(invalidUser);
    }
}
