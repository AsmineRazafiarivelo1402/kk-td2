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

        Ingredient i1 = new Ingredient(10, "Carotte", CategoryEnum.VEGETABLE, 0.40);
        Ingredient i2 = new Ingredient(6, "Poulet", CategoryEnum.ANIMAL, 2.50);
        Ingredient i3 = new Ingredient(7, "Lait", CategoryEnum.DAIRY, 0.60);
        Ingredient i4 = new Ingredient(8, "Fromage", CategoryEnum.DAIRY, 1.20);
        Ingredient i5 = new Ingredient(9, "Sel", CategoryEnum.OTHER, 0.10);
        Dish dish6 = new Dish();
        DishIngredient di1 = new DishIngredient( 6, dish6, i1,  new BigDecimal("100"),  Unit.KG);
        DishIngredient di2 = new DishIngredient(7,dish6, i2, new BigDecimal("200"),   Unit.KG);
        DishIngredient di3 = new DishIngredient(8,dish6, i3, new BigDecimal("1"), Unit.KG);
        DishIngredient di4 = new DishIngredient(9,dish6,i4, new BigDecimal("50"),Unit.KG);
        DishIngredient di5 = new DishIngredient( 10,  dish6,   i5, new BigDecimal("5"),Unit.KG);
        List<DishIngredient> listDishIngredient = List.of(di1,di2,di3,di4,di5);
        dish6.setId(5);
        dish6.setName("Pizza");
        dish6.setDishType(DishTypeEnum.MAIN);
        dish6.setIngredients(listDishIngredient);
        dish6.setSelling_price(3500.00);
        Dish dishTosave = dataRetriever.saveDish(dish6);
        System.out.println(dishTosave );





    }
}
