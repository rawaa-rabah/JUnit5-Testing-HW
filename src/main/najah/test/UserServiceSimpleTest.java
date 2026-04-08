package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.concurrent.TimeUnit;
import main.najah.code.UserService;

@DisplayName("User Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceSimpleTest {

    UserService userService;

    @BeforeAll
    static void initAll() {
        System.out.println("setup complete: Starting User Service Test Suite");
    }

    @BeforeEach
    void setUp() {
        userService = new UserService();
        System.out.println("setup complete: User Service is ready");
    }

    @AfterEach
    void tearDown() {
        System.out.println(" test method finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println(" all user service tests done");
    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {
        "test@gmail.com",
        "user.name@najah.edu",
        "a@b.c",
        "@."
    })
    @DisplayName("Valid Emails")
    void testValidEmails(String email) {
        assertTrue(userService.isValidEmail(email));
    }

    @Order(2)
    @Test
    @DisplayName("Invalid Emails")
    void testInvalidEmails() {
        assertAll(
            () -> assertFalse(userService.isValidEmail("testgmailcom")),
            () -> assertFalse(userService.isValidEmail("test@gmail")),
            () -> assertFalse(userService.isValidEmail("test.com")),
            () -> assertFalse(userService.isValidEmail(null))
        );
    }

    @Order(3)
    @ParameterizedTest
    @CsvSource({
        "admin, 1234,  true",
        "admin, wrong, false",
        "user,  1234,  false"
    })
    @DisplayName("Authentication cases")
    void testAuthenticate(String username, String password, boolean expected) {
        assertEquals(expected, userService.authenticate(username, password));
    }

    @Order(4)
    @Test
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Authentication success case")
    void testAuthenticateSuccess() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

  

    @Test
    @Order(6)
    @DisplayName("Authentication with null values")
    void testAuthenticateNull() {
        assertFalse(userService.authenticate(null, "1234"));
        assertFalse(userService.authenticate("admin", null));
    }
    
    
    @Order(7)
    @Test
    @Disabled("Failing: authenticate does not support empty password")
    @DisplayName(" failing test")
    void failingTest() {
        assertTrue(userService.authenticate("admin", ""));
    }
}