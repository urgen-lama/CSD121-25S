package lab7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoData() {
        var recipes = Main.getQuickRecipes(List::of);
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoQuickRecipes() {
        var recipes = Main.getQuickRecipes(() -> List.of(
                new Recipe(0, "", "", "", 4, 10, 10, 16),
                new Recipe(1, "", "", "", 4, 10, 10, 20),
                new Recipe(2, "", "", "", 4, 10, 10, 200)
        ));
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsAllRecipesIfAllQuick() {

        var recipes = Main.getQuickRecipes(() -> List.of(
                new Recipe(0, "", "", "", 4, 10, 10, 15),
                new Recipe(1, "", "", "", 4, 10, 10, 1),
                new Recipe(2, "", "", "", 4, 10, 10, 10)
        ));

        assertEquals(3, recipes.size());
    }

    @Test
    void testGetQuickRecipesWorksOnTypicalData() {

        var recipes = Main.getQuickRecipes(() -> List.of(
                        new Recipe(0, "", "", "", 4, 10, 10, 10),
                        new Recipe(1, "", "", "", 4, 10, 10, 15),
                        new Recipe(2, "", "", "", 4, 10, 10, 16),
                        new Recipe(3, "", "", "", 4, 10, 10, 20),
                        new Recipe(4, "", "", "", 4, 10, 10, 2343)
        ));

        assertEquals(2, recipes.size());

        // Verify that the two recipes we expected are in fact in the list
        assertEquals(0, recipes.get(0).id());
        assertEquals(1, recipes.get(1).id());
    }

    // TODO: test the searchRecipes method
    @Test
    void testSearchRecipesFindsMatchesByName() {
        var mockDataService = (DataService) () -> List.of(
                new Recipe(1, "Chicken Salad", "A tasty chicken dish.", "", 0, 0, 0, 0),
                new Recipe(2, "Beef Stew", "Hearty beef and vegetable stew.", "", 0, 0, 0, 0),
                new Recipe(3, "Roasted Chicken", "A simple roasted chicken.", "", 0, 0, 0, 0)
        );
        var recipes = Main.searchRecipes(mockDataService, "chicken");
        assertEquals(2, recipes.size());
        assertEquals("Chicken Salad", recipes.get(0).name());
        assertEquals("Roasted Chicken", recipes.get(1).name());
    }

    @Test
    void testSearchRecipesFindsMatchesByDescription() {
        var mockDataService = (DataService) () -> List.of(
                new Recipe(1, "Lemonade", "A refreshing drink.", "", 0, 0, 0, 0),
                new Recipe(2, "Apple Pie", "A classic dessert.", "", 0, 0, 0, 0),
                new Recipe(3, "Vegetable Soup", "A healthy and hearty soup.", "", 0, 0, 0, 0)
        );
        var recipes = Main.searchRecipes(mockDataService, "healthy");
        assertEquals(1, recipes.size());
        assertEquals("Vegetable Soup", recipes.get(0).name());
    }

    @Test
    void testSearchRecipesIsCaseInsensitive() {
        var mockDataService = (DataService) () -> List.of(
                new Recipe(1, "Chicken Soup", "A classic recipe.", "", 0, 0, 0, 0),
                new Recipe(2, "chicken and rice", "Simple chicken meal.", "", 0, 0, 0, 0)
        );
        var recipes = Main.searchRecipes(mockDataService, "Chicken");
        assertEquals(2, recipes.size());
    }

    @Test
    void testSearchRecipesReturnsEmptyListIfNoMatchesFound() {
        var mockDataService = (DataService) () -> List.of(
                new Recipe(1, "Pasta", "Italian pasta.", "", 0, 0, 0, 0),
                new Recipe(2, "Pizza", "A cheesy pizza.", "", 0, 0, 0, 0)
        );
        var recipes = Main.searchRecipes(mockDataService, "lasagna");
        assertTrue(recipes.isEmpty());
    }

    @Test
    void testSearchRecipesReturnsEmptyListOnException() {
        var mockDataService = (DataService) () -> {
            throw new RuntimeException("Simulated database error");
        };
        var recipes = Main.searchRecipes(mockDataService, "anything");
        assertTrue(recipes.isEmpty());
    }

    @Test
    void testSearchRecipesFindsMatchesInBothNameAndDescription() {
        var mockDataService = (DataService) () -> List.of(
                new Recipe(1, "Pasta Primavera", "A delicious pasta dish.", "", 0, 0, 0, 0),
                new Recipe(2, "Beef Lasagna", "A cheesy beef pasta bake.", "", 0, 0, 0, 0)
        );
        var recipes = Main.searchRecipes(mockDataService, "pasta");
        assertEquals(2, recipes.size());
        assertEquals("Pasta Primavera", recipes.get(0).name());
        assertEquals("Beef Lasagna", recipes.get(1).name());
    }
}