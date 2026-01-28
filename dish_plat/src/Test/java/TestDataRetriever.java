import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
         IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> { throw new IllegalArgumentException("Dish not found "+dishIdNotFind); }
            );

            assertEquals("Dish not found "+dishIdNotFind, exception.getMessage());
    }

    @Test
    public void testFindIngredients(){
        DataRetriever dataRetriever = new DataRetriever();
        List<Ingredient> listIngredient = dataRetriever.findIngredients(2,2);
        assertEquals(2,listIngredient.size());
        assertEquals("Poulet",listIngredient.getFirst().getName());
        assertEquals("Chocolat ", listIngredient.getLast().getName());
    }

    @Test
    public void testFindDishByIngredientName(){
        DataRetriever dataRetriever = new DataRetriever();
        List<Dish> dishes   = dataRetriever.findDishByIngredientName("eur");
        assertEquals(1,dishes.size());
        assertEquals("Gâteau au chocolat ",dishes.getFirst().getName());
    }
}


