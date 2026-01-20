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
        CategoryEnum category = CategoryEnum.VEGETABLE;
List<DishIngredient> dishIngredientList = dataRetriever.findIngredientsByCriteria("tomate",category, "salade", 1, 1);
        System.out.println(dishIngredientList);


    }
}
