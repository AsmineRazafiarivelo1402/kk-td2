import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestDataRetriever {


    @Test
    public void testFindDishById() {
      DataRetriever dataRetriever = new DataRetriever();
     Integer dishId= 1;
     Integer dishIdNotFind = 999;
        Dish dish = dataRetriever.findDishById(dishId);

        assertNotNull(dish);
        assertEquals("Salaide fraîche", dish.getName());
        assertEquals(2,dish.getDishIngredients().size());
        assertEquals("Laitue",dish.getDishIngredients().getFirst().getIngredient().getName());
        assertEquals("Tomate",dish.getDishIngredients().getLast().getIngredient().getName());



    }
}


