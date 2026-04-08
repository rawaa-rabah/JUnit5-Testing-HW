package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.concurrent.TimeUnit;
import main.najah.code.Product;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Product Tests")
public class ProductTest {

    Product product;

    @BeforeAll
    static void initAll() {
        System.out.println("setup complete: Starting Product Test Suite");
    }

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0);
        System.out.println("setup complete: new product instance created");
    }

    @AfterEach
    void tearDown() {
        System.out.println(" test method finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("all product tests done");
    }

    @Test
    @DisplayName("Valid product creation")
    void testValidProduct() {
        assertAll("Product creation",
            () -> assertEquals("Laptop", product.getName()),
            () -> assertEquals(1000.0, product.getPrice()),
            () -> assertEquals(0.0, product.getDiscount())
        );
    }
    
    @Test
    @DisplayName("Zero price is allowed")
    void testZeroPrice() {
        Product p = new Product("Item", 0.0);

        assertAll("Zero price product",
            () -> assertEquals("Item", p.getName()),
            () -> assertEquals(0.0, p.getPrice()),
            () -> assertEquals(0.0, p.getDiscount())
        );
    }
    

    @Test
    @DisplayName("Negative price throws exception")
    void testNegativePrice() {
        assertThrows(IllegalArgumentException.class,
            () -> new Product("Phone", -50.0));
    }

    @ParameterizedTest
    @CsvSource({
        "0,  1000.0",
        "10,  900.0",
        "25,  750.0",
        "50,  500.0"
    })
    @DisplayName("Valid discounts produce correct final price")
    void testValidDiscounts(double pct, double expected) {
        product.applyDiscount(pct);
        assertAll("Discount result",
            () -> assertEquals(expected, product.getFinalPrice(), 0.001),
            () -> assertEquals(pct, product.getDiscount())
        );
    }

    @Test
    @DisplayName("Discount of exactly 50 is accepted")
    void testMaxDiscount() {
        product.applyDiscount(50.0);
        assertEquals(500.0, product.getFinalPrice(), 0.001);
    }

    @Test
    @DisplayName("Negative discount throws exception")
    void testNegativeDiscount() {
        assertThrows(IllegalArgumentException.class,
            () -> product.applyDiscount(-1.0));
    }

    @Test
    @DisplayName("Discount above 50 throws exception")
    void testDiscountAbove50() {
        assertThrows(IllegalArgumentException.class,
            () -> product.applyDiscount(51.0));
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("getFinalPrice returns correct value with no discount")
    void testFinalPriceNoDiscount() {
        assertEquals(1000.0, product.getFinalPrice());
    }

    @Test
    @DisplayName("Final price with 20% discount")
    void testFinalPriceWith20Percent() {
        product.applyDiscount(20.0);
        assertEquals(800.0, product.getFinalPrice(), 0.001);
    }
    
    
    @Test
    @Disabled("Failing: product has no setPrice() method")
    @DisplayName("Suppressed failing test")
    void failingTest() {
        assertEquals(2000.0, product.getPrice());
    }
}