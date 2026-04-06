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
@DisplayName("Calculator Tests")
public class CalculatorTest {

    Calculator calc;

    @BeforeAll
    static void initAll() {
        System.out.println("start tests");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("setup done");
    }

    @Test
    @Order(1)
    @DisplayName("Addition test")
    void testAdd() {
        assertEquals(15, calc.add(10, 5));
        assertEquals(0, calc.add(0, 0));
        assertEquals(0, calc.add());
    }

    @Test
    @Order(2)
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Divide valid")
    void testDivideValid() {
        assertEquals(2, calc.divide(10, 5));
        assertEquals(3, calc.divide(9, 3));
    }

    @Test
    @Order(3)
    @DisplayName("Divide by zero")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }

    @ParameterizedTest
    @CsvSource({
        "0,1",
        "1,1",
        "5,120"
    })
    @Order(4)
    @DisplayName("Factorial test")
    void testFactorial(int input, int expected) {
        assertEquals(expected, calc.factorial(input));
    }

    @Test
    @Order(5)
    @DisplayName("Factorial negative")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }

    @Test
    @Order(6)
    @Disabled("intentional fail")
    @DisplayName("Failing test")
    void failingTest() {
        assertEquals(5, 2 + 2);
    }

    @AfterEach
    void tearDown() {
        System.out.println("test finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("all done");
    }
}