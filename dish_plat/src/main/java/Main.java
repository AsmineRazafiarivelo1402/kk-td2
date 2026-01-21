import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Log before changes
        DataRetriever dataRetriever = new DataRetriever();
//        Dish dish = dataRetriever.findDishById(4);
//        System.out.println(dish);
//        List<Dish> dishes   = dataRetriever.findDishsByIngredientName("tomate");
//        System.out.println(dishes);
//        List<Ingredient> ingredients = dataRetriever.findIngredients(1,2);
//        System.out.println(ingredients);
//        CategoryEnum category = CategoryEnum.VEGETABLE;
//List<DishIngredient> dishIngredientList = dataRetriever.findIngredientsByCriteria("tomate",category, "salade", 1, 1);
//        System.out.println(dishIngredientList);
        Ingredient ingredient = new Ingredient( 1 ," Laitue"  , CategoryEnum.VEGETABLE, 800.00);

                Ingredient tomate = new Ingredient( 2 ," Tomate"  , CategoryEnum.VEGETABLE, 600.00);
        Dish dish1 = new Dish();


        BigDecimal bd = new BigDecimal("0.20");
        BigDecimal bd1 = new BigDecimal("0.15");




        DishIngredient dishIngredient = new DishIngredient(  1, dish1,ingredient,bd , Unit.KG );
        DishIngredient dishIngredient1 = new DishIngredient(  2, dish1,tomate,bd1 , Unit.KG );
        List<DishIngredient> dishIngredientList = List.of(dishIngredient1,dishIngredient);
        dish1.setId(1);
        dish1.setName("Salaide fraîche");
        dish1.setDishType(DishTypeEnum.STARTER );
        dish1.setIngredients(dishIngredientList);
        dish1.setSelling_price(3500.00);
        System.out.println(dish1.getDishCost());

        System.out.println(dish1.getGrossMargin());
        System.out.println(dish1.getIngredients().get(0));
        System.out.println(dish1.getIngredients().get(1));

    }
}
