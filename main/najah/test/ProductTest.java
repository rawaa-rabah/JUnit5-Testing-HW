package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Timeout; 
import java.util.concurrent.TimeUnit;  
import main.najah.code.Product;

@DisplayName("Product Domain Tests")
@Execution(ExecutionMode.CONCURRENT) 
public class ProductTest {

    private Product product;

    @BeforeAll
    static void initAll() {
        System.out.println("setup complete: starting global product testing"); 
    }

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0);
        System.out.println("setup complete: product instance created"); 
    }

    @Test
    @DisplayName("Valid Product Initialization")
    void testProductCreation() {
        assertAll("Product basics",
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(1000.0, product.getPrice()),
            () -> assertEquals(0, product.getDiscount())
        );
    }

    @Test
    @DisplayName("Timeout Test")
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS) 
    void testFinalPriceTimeout() {
        double price = product.getFinalPrice();
        assertEquals(1000.0, price);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 1000.0",
        "10, 900.0",
        "50, 500.0"
    })
    @DisplayName("Parameterized Test: Discount Scenarios")
    void testApplyDiscount(double percentage, double expectedPrice) {
        product.applyDiscount(percentage);
        assertEquals(expectedPrice, product.getFinalPrice(), 0.001);
    }

    @Test
    @DisplayName("Invalid Input Tests")
    void testInvalidInputs() {
        assertAll("Exception checks",
            () -> assertThrows(IllegalArgumentException.class, () -> new Product("Bad", -1.0)),
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-5)),
            () -> assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(51))
        );
    }

    @Test
    @Disabled("Failing intentionally: price update logic needed")
    @DisplayName("Intentionally Failing Test (Disabled)")
    void testPriceUpdate() {
        assertEquals(2000.0, product.getPrice());
    }

    
    @Test
    @DisplayName(" Test Getters")
    void testAllGetters() {
        assertAll("Verify all fields",
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(1000.0, product.getPrice()),
            () -> assertEquals(0, product.getDiscount())
        );
    }

    @Test
    @DisplayName(" Test Constructor Logic")
    void testConstructor() {
        Product p = new Product("Test", 500);
        assertNotNull(p);
    }
    
    
    
    @AfterEach
    void tearDown() {
        System.out.println("test finished"); 
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("all product tests done"); 
    }
}