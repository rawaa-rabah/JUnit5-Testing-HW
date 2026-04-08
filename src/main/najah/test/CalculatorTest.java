package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import java.util.concurrent.TimeUnit;
import main.najah.code.Calculator;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Calculator Comprehensive Coverage Tests")
public class CalculatorTest {

    Calculator calc;

    @BeforeAll
    static void initAll() {
        System.out.println("Global Setup: Starting Calculator Test Suite");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    //  Addition Tests 
    @Test
    @Order(1)
    @DisplayName("Verify addition of two positive integers")
    void testAddPositiveNumbers() {
        assertEquals(15, calc.add(10, 5), "10 + 5 should be 15");
    }
    
    @Test
    @Order(2)
    @DisplayName("Add with no arguments returns zero")
    void testAddNoArgs() {
        assertEquals(0, calc.add()); 
    }
    
    @Test
    @Order(3)
    @DisplayName("Add three numbers")
    void testAddMultipleNumbers() {
        assertEquals(15, calc.add(3, 5, 7)); 
    }
    
      
    @Test
    @Order(3)
    @DisplayName("Verify addition with zero values")
    void testAddZero() {
        assertEquals(0, calc.add(0, 0), "0 + 0 should be 0");
    }

    @Test
    @Order(4)
    @DisplayName("Verify addition with negative and large numbers")
    void testAddVariations() {
        assertAll("Addition Variations",
            () -> assertEquals(-5, calc.add(-10, 5)),
            () -> assertEquals(1000000, calc.add(500000, 500000))
        );
    }

 
    
    
    // Division Tests 
    @Test
    @Order(5)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Check division for standard valid inputs")
    void testStandardDivision() {
        assertEquals(2, calc.divide(10, 5));
    }

    @Test
    @Order(6)
    @DisplayName("Ensure ArithmeticException is thrown on division by zero")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }

    @Test
    @Order(7)
    @DisplayName("Verify division with negative divisors")
    void testDivisionNegative() {
        assertEquals(-2, calc.divide(10, -5));
    }

    @Test
    @Order(8)
    @DisplayName("Division where result is zero")
    void testDivisionResultZero() {
        assertEquals(0, calc.divide(0, 5));
    }
    
    
    //  Factorial Tests 
    @ParameterizedTest
    @CsvSource({
        "0, 1",
        "1, 1",
        "5, 120",
        "10, 3628800"
    })
    @Order(9)
    @DisplayName("Calculate factorial for valid boundary values (0, 1, 5, 10)")
    void testFactorialValidCases(int input, int expected) {
        assertEquals(expected, calc.factorial(input));
    }

    @Test
    @Order(10)
    @DisplayName("Handle negative input in factorial with IllegalArgumentException")
    void testFactorialNegativeInput() {
        assertAll("Negative Factorials",
            () -> assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1)),
            () -> assertThrows(IllegalArgumentException.class, () -> calc.factorial(-10))
        );
    }

    //  Disabled Test 
    @Test
    @Order(9)
    @Disabled("Intentional failing test: correct result is 4")
    @DisplayName("Example of failing test")
    void failingTest() {
        assertEquals(5, 2 + 2);
    }

    @Test
    @Order(10)
    @DisplayName("Verify Calculator Instance Creation")
    void testConstructor() {
        assertNotNull(new Calculator());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Status: Individual test method completed");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Final: All calculator scenarios processed");
    }
}