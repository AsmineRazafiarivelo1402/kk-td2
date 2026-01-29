import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        // Log before changes
//        DataRetriever dataRetriever = new DataRetriever();
//        Dish dish = dataRetriever.findDishById(87);
//        System.out.println(dish);
//        List<Dish> dishes   = dataRetriever.findDishsByIngredientName("tomate");
//        System.out.println(dishes);
//        List<Ingredient> ingredients = dataRetriever.findIngredients(1,2);
//        System.out.println(ingredients);
//        CategoryEnum category = CategoryEnum.VEGETABLE;
//List<DishIngredient> dishIngredientList = dataRetriever.findIngredientsByCriteria("tomate",category, "salade", 1, 1);
//        System.out.println(dishIngredientList);
//        Ingredient ingredient = new Ingredient( 1 ," Laitue"  , CategoryEnum.VEGETABLE, 800.00);
//
//                Ingredient tomate = new Ingredient( 2 ," Tomate"  , CategoryEnum.VEGETABLE, 600.00);
//        Dish dish1 = new Dish();
//
//
//        BigDecimal bd = new BigDecimal("0.20");
//        BigDecimal bd1 = new BigDecimal("0.15");
//
//
//
//
//        DishIngredient dishIngredient = new DishIngredient(  1, dish1,ingredient,bd , Unit.KG );
//        DishIngredient dishIngredient1 = new DishIngredient(  2, dish1,tomate,bd1 , Unit.KG );
//        List<DishIngredient> dishIngredientList = List.of(dishIngredient1,dishIngredient);
//        dish1.setId(1);
//        dish1.setName("Salaide fraîche");
//        dish1.setDishType(DishTypeEnum.STARTER );
//        dish1.setDishIngredients(dishIngredientList);
//        dish1.setSelling_price(3500.00);
//        System.out.println(dish1.getDishCost());
//        System.out.println(dish1.getGrossMargin());
//
//        System.out.println(dish1.getIngredients().get(0));
//        System.out.println(dish1.getIngredients().get(1));
//
//        Ingredient i1 = new Ingredient(10, "Carotte", CategoryEnum.VEGETABLE, 0.40);
//        Ingredient i2 = new Ingredient(6, "Poulet", CategoryEnum.ANIMAL, 2.50);
//        Ingredient i3 = new Ingredient(7, "Lait", CategoryEnum.DAIRY, 0.60);
//        Ingredient i4 = new Ingredient(8, "Fromage", CategoryEnum.DAIRY, 1.20);
//        Ingredient i5 = new Ingredient(9, "Sel", CategoryEnum.OTHER, 0.10);
//        Dish dish6 = new Dish();
//        DishIngredient di1 = new DishIngredient( 6, dish6, i1,  new BigDecimal("100"),  Unit.KG);
//        DishIngredient di2 = new DishIngredient(7,dish6, i2, new BigDecimal("200"),   Unit.KG);
//        DishIngredient di3 = new DishIngredient(8,dish6, i3, new BigDecimal("1"), Unit.KG);
//        DishIngredient di4 = new DishIngredient(9,dish6,i4, new BigDecimal("50"),Unit.KG);
//        DishIngredient di5 = new DishIngredient( 10,  dish6,   i5, new BigDecimal("5"),Unit.KG);
//        List<DishIngredient> listDishIngredient = List.of(di1,di2,di3,di4,di5);
//        dish6.setId(5);
//        dish6.setName("Pizza");
//        dish6.setDishType(DishTypeEnum.MAIN);
//        dish6.setDishIngredients(listDishIngredient);
//        dish6.setSelling_price(3500.00);
//        Dish dishTosave = dataRetriever.saveDish(dish6);
//        System.out.println(dishTosave );
//
//        Ingredient ingredient20 = new Ingredient(); ingredient20.setId(20); ingredient20.setName("Riz"); ingredient20.setCategory(CategoryEnum.OTHER); ingredient20.setPrice(1200.0);
//
//        DishIngredient di20 = new DishIngredient(); di20.setId(20); di20.setIngredient(ingredient20); di20.setQuantity_required(new BigDecimal("0.5")); di20.setUnit_tupe(Unit.KG);
//
//        Ingredient ingredient21 = new Ingredient(); ingredient21.setId(21); ingredient21.setName("Huile"); ingredient21.setCategory(CategoryEnum.VEGETABLE); ingredient21.setPrice(3000.0);
//
//        DishIngredient di21 = new DishIngredient(); di21.setId(21); di21.setIngredient(ingredient21); di21.setQuantity_required(new BigDecimal("0.05")); di21.setUnit_tupe(Unit.KG);
//
//        Dish dish = new Dish(); dish.setId(50); dish.setName("Riz sauté"); dish.setDishType(DishTypeEnum.MAIN); dish.setSelling_price(8000.0); dish.setDishIngredients(List.of(di20, di21));
//
//        di20.setDish(dish); di21.setDish(dish);
//
//        System.out.println(dataRetriever.saveDish(dish));
//
//        Ingredient ingredient22 = new Ingredient(); ingredient22.setId(22); ingredient22.setName("Viande"); ingredient22.setCategory(CategoryEnum.ANIMAL); ingredient22.setPrice(6000.0);
//
//        DishIngredient di22 = new DishIngredient(); di22.setId(22); di22.setIngredient(ingredient22); di22.setQuantity_required(new BigDecimal("0.3")); di22.setUnit_tupe(Unit.KG);
//
//        Dish dishtosave2 = new Dish(); dishtosave2.setId(50); dishtosave2.setName("Riz sauté spécial"); dishtosave2.setDishType(DishTypeEnum.MAIN); dishtosave2.setSelling_price(9000.0); dishtosave2.setDishIngredients(List.of(di22));
//
//        di22.setDish(dishtosave2);
//
//        System.out.println(dataRetriever.saveDish(dishtosave2));
//        List<StockMovement> stockMovementList = new ArrayList<>();
//        Ingredient ail = new Ingredient(45, "Ail", 0.10, CategoryEnum.OTHER,stockMovementList);
//        Ingredient toSave = dataRetriever.saveIngredient(ail);
//        StockValue value = new StockValue(5.0, Unit.KG);
//        StockMovement stock1 = new StockMovement(1,Movementtype.IN, Instant.parse("2024-01-05T08:00:00Z"),value);
//        StockValue value1 = new StockValue(0.2,Unit.KG);
//        StockMovement stock2 = new StockMovement(2,Movementtype.OUT ,Instant.parse("2024-01-06T12:00:00Z"),value1);
//        List<StockMovement> stockMovements = List.of(stock1,stock2);
//        Ingredient laitue = new Ingredient(1,"Laitue",800.00, CategoryEnum.VEGETABLE,stockMovements);
//        System.out.println(laitue.getStockValueAt(Instant.parse("2024-01-06T12:00:00Z")));
// ===== StockValue 1 =====
//        StockValue stockValue1 = new StockValue(); stockValue1.setQuantity(50.0); stockValue1.setUnit(Unit.KG);
//
//        // ===== StockMovement 1 (IN) =====
//        StockMovement stock1 = new StockMovement(); stock1.setId(20); stock1.setValue(stockValue1); stock1.setMovementtype(Movementtype.IN); stock1.setCreationDateTime(Instant.now());
//
//        // ===== StockValue 2 =====
//        StockValue stockValue2 = new StockValue(); stockValue2.setQuantity(10.0); stockValue2.setUnit(Unit.KG);
//
//        // ===== StockMovement 2 (OUT) =====
//        StockMovement stock2 = new StockMovement(); stock2.setId(21); stock2.setValue(stockValue2); stock2.setMovementtype(Movementtype.OUT); stock2.setCreationDateTime(Instant.now());

//        // ===== Ingredient =====
//        Ingredient ingredient = new Ingredient();
//        ingredient.setId(20);
//        ingredient.setName("Been");
//        ingredient.setCategory(CategoryEnum.VEGETABLE);
//        ingredient.setPrice(1200.0);
//        ingredient.setStockMovementList(List.of(stock1, stock2));
//
//        // ===== Save =====
//
//        dataRetriever.saveIngredient(ingredient);
//
//        System.out.println("Ingredient et mouvements de stock enregistrés avec succès");
//        Dish dish1 = dataRetriever.findDishById(3);
//        System.out.println(dish1);
//        DishOrder dishOrder = new DishOrder(dish1,1);
//        List<DishOrder> dishOrderList = new ArrayList<>();
//        dishOrderList.add(dishOrder);
//        Order order = new Order(Instant.now(),dishOrderList, "ORD00003");
//        Order orderSave = dataRetriever.saveOrder(order);
//        Order orderfind = dataRetriever.findOrderByReference("ORD00003");
//        System.out.println(orderfind);
//        System.out.println(orderSave);


        DataRetriever dataRetriever = new DataRetriever();

//        // 1️⃣ Récupérer un plat existant
//        Dish dish = dataRetriever.findDishById(2);
//        System.out.println("Plat trouvé : " + dish);
//
//        // 2️⃣ Créer DishOrder (plat + quantité)
//        DishOrder dishOrder = new DishOrder(
//                dish,   // plat
//                1,      // quantité de plats commandés
//                4      // id client (ou autre selon ton modèle)
//        );
//
//        List<DishOrder> dishOrders = new ArrayList<>();
//        dishOrders.add(dishOrder);
//
//        // 3️⃣ Créer la commande
//        Order orderToSave = new Order(
//                Instant.now(),   // date création
//                dishOrders,      // liste des plats
//                3,               // id client
//                null             // reference (générée en DB)
//        );
//
//        // 4️⃣ Sauvegarder la commande
//        Order savedOrder = dataRetriever.saveOrder(orderToSave);
//
//        // 5️⃣ Rechercher la commande par référence
//        Order foundOrder =
//                dataRetriever.findOrderByReference(
//                        savedOrder.getReference()
//                );
//
//        // 6️⃣ Affichage des résultats
//        System.out.println("Commande sauvegardée : " + savedOrder);
//        System.out.println("Commande retrouvée   : " + foundOrder);


//        // ✅ 1️⃣ Récupérer un plat existant
//        Dish dish1 = dataRetriever.findDishById(2);
//
//        // Créer DishOrder : 1 plat commandé
//        DishOrder dishOrder = new DishOrder(dish1, 2, 1);
//        List<DishOrder> dishOrderList = new ArrayList<>();
//        dishOrderList.add(dishOrder);
//
//        // ✅ 2️⃣ Créer une commande avec référence manuelle ORD00002
//        Order manualOrder = new Order(
//                        Instant.now(),
//                        dishOrderList,
//                        1,
//                        "ORD00002",
//                StatusOrder.CREATED,
//                OrderType.TAKE_AWAY
//                );
//
//
//        // ✅ 3️⃣ Sauvegarder la commande
//        Order savedManualOrder = dataRetriever.saveOrder(manualOrder);
//
//        // ✅ 4️⃣ Récupérer la commande pour vérifier
//        Order foundOrder = dataRetriever.findOrderByReference("ORD00002");
//
//        // ✅ 5️⃣ Affichage pour test
//        System.out.println("Commande sauvegardée : " + savedManualOrder);
//        System.out.println("Commande retrouvée : " + foundOrder);

// 🔹 1️⃣ Récupérer un plat depuis la base
        Dish dish2 = dataRetriever.findDishById(3); // suppose que ce plat existe en DB

// 🔹 Créer DishOrder : 1 plat commandé, quantité 3
        DishOrder dishOrder2 = new DishOrder(dish2, 3, 1); // id fictif du DishOrder
        List<DishOrder> dishOrderList2 = new ArrayList<>();
        dishOrderList2.add(dishOrder2);

// 🔹 2️⃣ Créer une commande avec référence manuelle ORD00003
        Order manualOrder2 = new Order(
                Instant.now(),             // date de création
                dishOrderList2,            // liste de plats
                2,                         // id (peut être id client ou autre)
                "ORD00003",                // référence manuelle
                StatusOrder.CREATED,       // statut initial
                OrderType.EAT_IN           // type de la commande
        );

// 🔹 3️⃣ Sauvegarder la commande
        Order savedManualOrder2 = dataRetriever.saveOrder(manualOrder2);

// 🔹 4️⃣ Récupérer la commande pour vérifier
        Order foundOrder2 = dataRetriever.findOrderByReference("ORD00003");

// 🔹 5️⃣ Affichage pour test
        System.out.println("Commande sauvegardée : " + savedManualOrder2);
        System.out.println("Commande retrouvée : " + foundOrder2);

// 🔹 6️⃣ Tester la règle DELIVERED
        foundOrder2.setStatusOrder(StatusOrder.DELIVERED);
        try {
            dataRetriever.saveOrder(foundOrder2);
        } catch (RuntimeException e) {
            System.out.println("Test règle DELIVERED OK : " + e.getMessage());
        }

    }


        }




