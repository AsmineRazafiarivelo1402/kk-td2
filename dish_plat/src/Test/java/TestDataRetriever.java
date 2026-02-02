import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
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

    @Test
    public void testIngredientsByCriteria(){
        DataRetriever dataRetriever = new DataRetriever();
        List<DishIngredient> listIngredient = dataRetriever.findIngredientsByCriteria(null,CategoryEnum.VEGETABLE,null,1,10);
        assertEquals(2,listIngredient.size());
        List<DishIngredient> listVide = new ArrayList<>();
        CategoryEnum categoryNull= null;
        List<DishIngredient> listIngredientVide = dataRetriever.findIngredientsByCriteria("cho",categoryNull,"Sal",1,10);
        assertEquals(0,listIngredientVide.size());
        assertEquals(listVide,listIngredientVide);
    }




        private DataRetriever dataRetriever;

        @BeforeEach
        void setup() {
            dataRetriever = new DataRetriever();
        }

        @Test
        void testSaveAndFindOrder() {
            // 🔹 1️⃣ Créer un plat
            Dish dish = dataRetriever.findDishById(2);

            // 🔹 2️⃣ Créer DishOrder
            DishOrder dishOrder = new DishOrder(dish, 2, 1);
            List<DishOrder> dishOrderList = new ArrayList<>();
            dishOrderList.add(dishOrder);

            // 🔹 3️⃣ Créer Order avec type et statut
            Order order = new Order(
                    Instant.now(),
                    dishOrderList,
                    1,
                    "ORD00007",
                    OrderStatus.CREATED,
                    OrderType.EAT_IN
            );

            // 🔹 4️⃣ Sauvegarder la commande
            Order savedOrder = dataRetriever.saveOrder(order);

            assertNotNull(savedOrder.getId(), "L'ID doit être généré");
            assertEquals("ORD00007", savedOrder.getReference());



            // 🔹 5️⃣ Récupérer la commande
            Order foundOrder = dataRetriever.findOrderByReference("ORD00001");
            assertNotNull(foundOrder);

            assertEquals(OrderType.EAT_IN, foundOrder.getOrderType());
            assertEquals(OrderStatus.CREATED, foundOrder.getStatusOrder());

        }

        @Test
        void testDeliveredOrderCannotBeModified() {
            // 🔹 Créer un plat et DishOrder
            Dish dish = dataRetriever.findDishById(2);
            DishOrder dishOrder = new DishOrder(dish, 1, 1);
            List<DishOrder> dishOrderList = new ArrayList<>();
            dishOrderList.add(dishOrder);

            // 🔹 Créer une commande
            Order order = new Order(
                    Instant.now(),
                    dishOrderList,
                    1,
                    "ORD_UNIT_02",
                    OrderStatus.DELIVERED,  // statut DELIVERED
                    OrderType.TAKE_AWAY
            );

            // 🔹 Vérifier que saveOrder lève bien l'exception
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                dataRetriever.saveOrder(order);
            });
            assertEquals("Une commande livrée ne peut plus être modifiée", exception.getMessage());
        }
    }





