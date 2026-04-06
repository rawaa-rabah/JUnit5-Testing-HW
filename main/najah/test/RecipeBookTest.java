package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.Timeout; 
import java.util.concurrent.TimeUnit; 

import main.najah.code.Recipe;
import main.najah.code.RecipeBook;
import java.time.Duration;

@DisplayName("Recipe Book Tests")
@Execution(ExecutionMode.CONCURRENT) 
public class RecipeBookTest {

    private RecipeBook recipeBook;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeAll
    static void initAll() {
        System.out.println("setup complete-> start tests");
    }

    @BeforeEach
    void setUp() {
        recipeBook = new RecipeBook();
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe2 = new Recipe();
        recipe2.setName("Tea");
        System.out.println("setup complete-> new test");
    }

    @Test
    @DisplayName("Add recipe + duplicate check")
    void testAddRecipe() {
        assertTrue(recipeBook.addRecipe(recipe1));
        assertFalse(recipeBook.addRecipe(recipe1)); 
    }

    @Test
    @DisplayName("Add recipe when full")
    void testAddRecipeFull() {
        recipeBook.addRecipe(recipe1);
        recipeBook.addRecipe(recipe2);
        Recipe r3 = new Recipe(); r3.setName("R3");
        Recipe r4 = new Recipe(); r4.setName("R4");
        recipeBook.addRecipe(r3);
        recipeBook.addRecipe(r4);
        Recipe r5 = new Recipe(); r5.setName("R5");
        assertFalse(recipeBook.addRecipe(r5));
    }

    @Test
    @DisplayName("Delete recipe valid")
    void testDeleteRecipe() {
        recipeBook.getRecipes()[0] = recipe1;
        assertEquals("Coffee", recipeBook.deleteRecipe(0));
    }

    @Test
    @DisplayName("Edit recipe")
    void testEditRecipe() {
        recipeBook.addRecipe(recipe1);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Latte");
        String oldName = recipeBook.editRecipe(0, newRecipe);
        assertAll("Edit verification",
            () -> assertEquals("Coffee", oldName),
            () -> assertEquals("", newRecipe.getName())
        );
    }

    @Test
    @DisplayName("Timeout Test")
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS) // 
    void testTimeoutScenario() {
        recipeBook.addRecipe(recipe1);
        assertNotNull(recipeBook.getRecipes()[0]);
    }

    @Test
    @DisplayName("Exception handling check")
    void testBoundaries() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            recipeBook.deleteRecipe(10);
        });
    }

    @Test
    @Disabled("Intentional fail - null recipe not handled")
    @DisplayName("Failing test")
    void failingTest() {
        assertTrue(recipeBook.addRecipe(null));
    }

    @AfterEach
    void tearDown() {
        System.out.println("test finished");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("all tests done");
    }
}