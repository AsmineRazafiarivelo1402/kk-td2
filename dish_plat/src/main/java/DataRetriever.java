import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataRetriever {
//    Dish findDishById(Integer id) {
//        DBConnection dbConnection = new DBConnection();
//        Connection connection = dbConnection.getConnection();
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(
//                    """
//
//                            select id_dish,dish.id, dish.name, dish.dish_type, dish.selling_price  from DishIngredient join dish on dish.id = id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = ?;
//
//                            """);
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//
//                Dish dish = new Dish();
//
//                dish.setId(resultSet.getInt("id_dish"));
//                dish.setName(resultSet.getString("name"));
//                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
//
//                dish.setDishIngredients(findDishIngredientByDishId(id));
//                return dish;
//            }
//            dbConnection.closeConnection(connection);
//            throw new RuntimeException("Dish not found " + id);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


Dish findDishById(Integer id) {
    DBConnection dbConnection = new DBConnection();
    Connection connection = dbConnection.getConnection();
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                        select dish.id as dish_id, dish.name as dish_name, dish_type, dish.selling_price as dish_price
                        from dish
                        where dish.id = ?;
                        """);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Dish dish = new Dish();
            dish.setId(resultSet.getInt("dish_id"));
            dish.setName(resultSet.getString("dish_name"));
            dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
            dish.setSelling_price(resultSet.getObject("dish_price") == null
                    ? null : resultSet.getDouble("dish_price"));
            dish.setDishIngredients(findDishIngredientByDishId(id));
            return dish;
        }
        dbConnection.closeConnection(connection);
        throw new RuntimeException("Dish not found " + id);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    private List<DishIngredient> findDishIngredientByDishId (Integer idDish){
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        List<DishIngredient> ingredients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    
                            select id_ingredient, ingredient.id, ingredient.name, ingredient.price,ingredient.category from DishIngredient join dish on dish.id= id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = ?
                                                         

                       """);
            preparedStatement.
                    setInt(1, idDish);
            ResultSet resultSet =
                    preparedStatement.executeQuery();
            while (resultSet.next()) {
                DishIngredient
                        dishingredient = new DishIngredient();
                Ingredient ingredient = new Ingredient();

                ingredient.setId(resultSet.getInt("id_ingredient"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));

                ingredient.setCategory(
                        CategoryEnum.valueOf(resultSet.getString(
                                "category")));

                dishingredient.
                        setIngredient(ingredient);
                ingredients.add(dishingredient);

            }
            dbConnection.closeConnection(connection);
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ingredient> findIngredients(int page, int size){
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select ingredient.id, ingredient.name, ingredient.price, ingredient.category
                            from ingredient     limit ? offset  ?;
                            """);
            preparedStatement.setInt(1, page);
            preparedStatement.setInt(2, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
                ingredients.add(ingredient);
            }
            dbConnection.closeConnection(connection);
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    List<Dish> findDishByIngredientName(String ingredientName){
        String dishSelect = """
                 select dish.id, dish.name, dish.dish_type, dish.selling_price
                            from DishIngredient join dish on dish.id = DishIngredient.id_dish
                                join ingredient on ingredient.id= id_ingredient
                            where ingredient.name ilike ?
                """;
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        List<Dish> dishes = new ArrayList<>();
        Integer id_dish;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dishSelect);
            preparedStatement.setString(1, "%" + ingredientName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
                id_dish = resultSet.getInt("id");
                dish.setDishIngredients(findDishIngredientByDishId(id_dish));
                dishes.add(dish);
                return dishes;
            }
            dbConnection.closeConnection(connection);
            throw new RuntimeException("Dish not found with ingredient " + ingredientName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<DishIngredient> findIngredientsByCriteria(
            String ingredientName,
            CategoryEnum category,
            String dishName,
            int page,
            int size
    ) {
        DBConnection dbConnection = new DBConnection();
        List<DishIngredient> dishIngredientList = new ArrayList<>();

        String sql = """
        SELECT 
            id_ingredient,
            id_dish,
            dish.name AS dish_name,
            ingredient.id AS idIngredient,
            ingredient.name AS ingredient_name,
            ingredient.price,
            ingredient.category
        FROM DishIngredient
        JOIN dish ON dish.id = id_dish
        JOIN ingredient ON ingredient.id = id_ingredient
        WHERE
            (? IS NULL OR ingredient.name ILIKE ?)
        AND
            (?::ingredient_category IS NULL OR ingredient.category = ?::ingredient_category)
        AND
            (? IS NULL OR dish.name ILIKE ?)
        LIMIT ? OFFSET ?
    """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // 1 & 2 → ingredientName
            if (ingredientName == null) {
                preparedStatement.setNull(1, Types.VARCHAR);
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(1, ingredientName);
                preparedStatement.setString(2, "%" + ingredientName + "%");
            }

            // 3 & 4 → category (ENUM PostgreSQL)
            if (category == null) {
                preparedStatement.setNull(3, Types.OTHER); // important pour ENUM
                preparedStatement.setNull(4, Types.OTHER);
            } else {
                preparedStatement.setString(3, category.name());
                preparedStatement.setString(4, category.name());
            }

            // 5 & 6 → dishName
            if (dishName == null) {
                preparedStatement.setNull(5, Types.VARCHAR);
                preparedStatement.setNull(6, Types.VARCHAR);
            } else {
                preparedStatement.setString(5, dishName);
                preparedStatement.setString(6, "%" + dishName + "%");
            }

            // 7 → LIMIT
            preparedStatement.setInt(7, size);

            // 8 → OFFSET
            preparedStatement.setInt(8, (page - 1) * size);

            // ---------- EXECUTION ----------
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Ingredient
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(resultSet.getInt("idIngredient"));
                    ingredient.setName(resultSet.getString("ingredient_name"));
                    ingredient.setPrice(resultSet.getDouble("price"));
                    ingredient.setCategory(
                            CategoryEnum.valueOf(resultSet.getString("category"))
                    );

                    // Dish
                    Dish dish = new Dish();
                    dish.setId(resultSet.getInt("id_dish"));
                    dish.setName(resultSet.getString("dish_name"));

                    // DishIngredient
                    DishIngredient dishIngredient = new DishIngredient();
                    dishIngredient.setIngredient(ingredient);
                    dishIngredient.setDish(dish);

                    dishIngredientList.add(dishIngredient);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return dishIngredientList;
    }

Dish saveDish(Dish toSave) {
String upsertDishSql = """
                    INSERT INTO dish (id,  name, dish_type,selling_price)
                    VALUES (?,  ?, ?::dish_type,?)
                    ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        dish_type = EXCLUDED.dish_type,
                            selling_price = EXCLUDED.selling_price
                    RETURNING id
                """;

        try (Connection conn = new DBConnection().getConnection()) {
            conn.setAutoCommit(false);
            Integer dishId;
            try (PreparedStatement ps = conn.prepareStatement(upsertDishSql)) {
                if (toSave.getId() != null) {
                    ps.setInt(1, toSave.getId());
                } else {
                    ps.setInt(1, getNextSerialValue(conn, "dish", "id"));
                }
                if (toSave.getSelling_price()!= null) {
                    ps.setDouble(4, toSave.getSelling_price());
                } else {
                    ps.setNull(4, Types.DOUBLE);
                }
                ps.setString(2, toSave.getName());
                ps.setString(3, toSave.getDishType().name());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    dishId = rs.getInt(1);
                }
            }

            List<DishIngredient> newIngredients = toSave.getDishIngredients();
            detachDishIngredient(conn, dishId, newIngredients);
            saveIngredientByDishIngredient(conn,dishId, newIngredients);
            attachDishIngredient(conn, dishId, newIngredients);

            conn.commit();
            return findDishById(dishId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveIngredientByDishIngredient(Connection conn, Integer dishId, List<DishIngredient> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) {
            return;
        }
           String insertSql = """
                  INSERT INTO ingredient (id,  name, price,category)
                    VALUES (?,  ?, ?,?::ingredient_category)
                    ON CONFLICT (id) DO UPDATE
                    SET name = EXCLUDED.name,
                        category = EXCLUDED.category,
                            price = EXCLUDED.price
                 
                """;
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (DishIngredient di : dishIngredients) {
                ps.setInt(1, di.getIngredient().getId());
                ps.setString(2, di.getIngredient().getName());
                ps.setString(4, di.getIngredient().getCategory().name());
                ps.setDouble(3, di.getIngredient().getPrice());
                ps.executeUpdate(); // ✅ EXECUTE DANS LA BOUCLE
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void detachDishIngredient(Connection conn,Integer dishId, List<DishIngredient> dishIngredients){
        String deleteDish = """
                DELETE FROM dishIngredient
                WHERE id_dish = ?;
                
                """;
        if (dishIngredients == null || dishIngredients.isEmpty()) {
                return;
        }
        try (PreparedStatement ps = conn.prepareStatement(deleteDish)) {

            ps.setInt(1, dishId);

           ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void attachDishIngredient(Connection conn,Integer dishId, List<DishIngredient> dishIngredients){
        String insertDishIngredient = """
                 INSERT INTO dishingredient (id,  id_dish, id_ingredient,quantity_required,unit)
                    VALUES (?,  ?, ?,?,?::unit_type)
                             """;
        if (dishIngredients == null || dishIngredients.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement(insertDishIngredient)) {
            for(DishIngredient dishIngredient: dishIngredients){
                ps.setInt(1,dishIngredient.getId());
                ps.setInt(2,dishIngredient.getDish().getId());
                ps.setInt(3,dishIngredient.getIngredient().getId());
                ps.setDouble(4,dishIngredient.getQuantity_required());
                ps.setString(5,dishIngredient.getUnit_tupe().name());
                ps.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private int getNextSerialValue(Connection conn, String tableName, String columnName)
            throws SQLException {

        String sequenceName = getSerialSequenceName(conn, tableName, columnName);
        if (sequenceName == null) {
            throw new IllegalArgumentException(
                    "Any sequence found for " + tableName + "." + columnName
            );
        }
        updateSequenceNextValue(conn, tableName, columnName, sequenceName);

        String nextValSql = "SELECT nextval(?)";

        try (PreparedStatement ps = conn.prepareStatement(nextValSql)) {
            ps.setString(1, sequenceName);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }
    private void detachIngredients(Connection conn, Integer dishId, List<DishIngredient> dishIngredients)
            throws SQLException {
        if (dishIngredients == null || dishIngredients.isEmpty()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE DishIngredient SET id_dish = NULL WHERE id_dish = ?")) {
                ps.setInt(1, dishId);
                ps.executeUpdate();
            }
            return;
        }

        String baseSql = """
                    UPDATE DishIngredient
                    SET id_dish = NULL
                    WHERE id_dish = ? AND id NOT IN (%s)
                """;

        String inClause = dishIngredients.stream()
                .map(i -> "?")
                .collect(Collectors.joining(","));

        String sql = String.format(baseSql, inClause);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dishId);
            int index = 2;
            for (DishIngredient dishIngredient : dishIngredients) {
                ps.setInt(index++, dishIngredient.getId());
            }
            ps.executeUpdate();
        }
    }
    private void attachIngredients(Connection conn, Integer dishId, List<DishIngredient> dishIngredients)
            throws SQLException {

        if (dishIngredients == null || dishIngredients.isEmpty()) {
            return;
        }


    }
    private String getSerialSequenceName(Connection conn, String tableName, String columnName)
            throws SQLException {

        String sql = "SELECT pg_get_serial_sequence(?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tableName);
            ps.setString(2, columnName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return null;
    }
    private void updateSequenceNextValue(Connection conn, String tableName, String columnName, String sequenceName) throws SQLException {
        String setValSql = String.format(
                "SELECT setval('%s', (SELECT COALESCE(MAX(%s), 0) FROM %s))",
                sequenceName, columnName, tableName
        );

        try (PreparedStatement ps = conn.prepareStatement(setValSql)) {
            ps.executeQuery();
        }
    }

    public Ingredient saveIngredient(Ingredient toSave) {

        if (toSave == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }

        String insertSql = """
        INSERT INTO ingredient (id, name, price, category)
        VALUES (?, ?, ?, ?::ingredient_category)
        ON CONFLICT (id) DO UPDATE
        SET name = EXCLUDED.name,
            category = EXCLUDED.category,
            price = EXCLUDED.price
            RETURNING id
    """;

        try (Connection conn = new DBConnection().getConnection()){
            Integer ingredientId;
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(insertSql)){
                if (toSave.getId() != null) {
                    ps.setInt(1, toSave.getId());
                } else {
                    int generatedId = getNextSerialValue(conn, "ingredient", "id");
                    ps.setInt(1, generatedId);
                    toSave.setId(generatedId);
                }

                ps.setString(2, toSave.getName());

                if (toSave.getPrice() != null) {
                    ps.setDouble(3, toSave.getPrice());
                } else {
                    ps.setNull(3, Types.DOUBLE);
                }

                ps.setString(4, toSave.getCategory().name());
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    ingredientId = rs.getInt(1);
                }
              }



            saveStockMovementList(conn, toSave); // ✅ même connexion

            conn.commit();
            return findIngredientById(ingredientId);
            // return findIngredientById(), need to create this method // copyPast on dish

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ingredient findIngredientById(Integer ingredientId) {
        DBConnection dbConnection = new DBConnection();
        String findsql = "Select id,name,price,category from ingredient where id = ?";
        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(findsql);
            preparedStatement.setInt(1,ingredientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int idIngredient = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                CategoryEnum categoryEnum = CategoryEnum.valueOf(resultSet.getString("category"));
                return new Ingredient(idIngredient,name,price,categoryEnum,findStockMovementsByIngredientId(idIngredient));
            }
        throw new RuntimeException("Ingredient not found " + ingredientId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<StockMovement> findStockMovementsByIngredientId(int idIngredient) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String findSql = "select id,quantity,unit,type,creation_datetime from stockmovement where stockmovement.id_ingredient = ? ";
        List<StockMovement> stockMovementList = new ArrayList<>();
        try{PreparedStatement preparedStatement = connection.prepareStatement(findSql);
            preparedStatement.setInt(1,idIngredient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                StockMovement stockMovement = new StockMovement();
                stockMovement.setId(resultSet.getInt("id"));
                stockMovement.setMovementtype(Movementtype.valueOf(resultSet.getString("type")));
                stockMovement.setCreationDateTime(resultSet.getTimestamp("creation_datetime").toInstant());

                StockValue stockValue = new StockValue();
                stockValue.setQuantity(resultSet.getDouble("quantity"));
                stockValue.setUnit(Unit.valueOf(resultSet.getString("unit")));
                stockMovement.setValue(stockValue);
                stockMovementList.add(stockMovement);
            }
            dbConnection.closeConnection(connection);
            return stockMovementList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveStockMovementList(Connection conn, Ingredient ingredient) {

        if (ingredient.getStockMovementList() == null || ingredient.getStockMovementList().isEmpty()) {
            return;
        }
    // ?:: on caste les données
        String insertStockMovement = """
        INSERT INTO StockMovement (id, id_ingredient, quantity, type, unit, creation_datetime)
        VALUES (?, ?, ?, ?::mouvement_type, ?::unit_type, ?)
        ON CONFLICT (id) DO NOTHING
    """;

        try (PreparedStatement ps = conn.prepareStatement(insertStockMovement)) {

            for (StockMovement stock : ingredient.getStockMovementList()) {
            //Update   with condition if/else/getNextValue
                if(ingredient.getId() != null){
                    ps.setInt(1,stock.getId());
                }else{
                    ps.setInt(1,getNextSerialValue(conn,"stockmovement", "id"));
                }
                ps.setInt(2, ingredient.getId());
                ps.setDouble(3, stock.getValue().getQuantity());
                ps.setObject(4,stock.getMovementtype());
                ps.setObject(5, stock.getValue().getUnit()); // CHANGE SETOBJECT
                ps.setTimestamp(6, Timestamp.from(stock.getCreationDateTime())); // ✅ CONVERTIR INSTANT À TIMESTAMP
                //correction
                ps.addBatch();
                //ps.executeUpdate();
            }
        ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void verifyIngredientStock(Order order) {

        for (DishOrder dishOrder : order.getDishOrders()) {

            int dishQuantity = dishOrder.getQuantity();
            Dish dish = dishOrder.getDish();

            for (DishIngredient dishIngredient : dish.getDishIngredients()) {

                Ingredient ingredient = dishIngredient.getIngredient();

                double requiredQuantity =
                        dishQuantity * dishIngredient.getQuantity_required();

                double availableStock =
                        ingredient.getStockValueAt(Instant.now()).getQuantity();

                if (availableStock < requiredQuantity) {
                    throw new RuntimeException(
                            "Stock insuffisant pour l'ingrédient : "
                                    + ingredient.getName()
                                    + " | requis=" + requiredQuantity
                                    + " | disponible=" + availableStock
                    );
                }
            }
        }
    }

    private boolean orderExists(String orderReference) {

        String sql = """
        SELECT 1
        FROM "order"
        WHERE reference_order = ?
    """;

        try (Connection connection = new DBConnection().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, orderReference);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si la référence existe
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence de la commande", e);
        }
    }

public Order saveOrder(Order orderToSave) {

    for (DishOrder dishOrder : orderToSave.getDishOrders()) {
        Dish dish = dishOrder.getDish();
        int dishQuantity = dishOrder.getQuantity();

        for (DishIngredient dishIngredient : dish.getDishIngredients()) {
            Ingredient ingredient = dishIngredient.getIngredient();
            double requiredQuantity = dishQuantity * dishIngredient.getQuantity_required();

            StockValue stockValue = ingredient.getStockValueAt(orderToSave.getCreationDatetime());

            if (stockValue == null || stockValue.getQuantity() < requiredQuantity) {
                throw new RuntimeException(
                        "Stock insuffisant pour l'ingrédient : "
                                + ingredient.getName()
                                + " | requis=" + requiredQuantity
                                + " | disponible=" + (stockValue == null ? 0 : stockValue.getQuantity())
                );
            }
        }
    }
    boolean hasReference = orderToSave.getReference() != null;
    boolean existsInDb = false;

    if (hasReference) {
        existsInDb = orderExists(orderToSave.getReference());
    }

    String sql;

    // 2️⃣ Choix du SQL selon les 3 cas
    if (!hasReference) {
        // CAS 1 : nouvelle commande, référence auto
        sql = """
            INSERT INTO "order" (reference_order, creation_datetime, order_status, order_type)
            VALUES (
                'ORD' || LPAD(nextval('order_reference_seq')::text, 5, '0'),
                ?, ?::orderStatus, ?::Ordertype
            )
            RETURNING id, reference_order
        """;

    } else if (!existsInDb) {
        // CAS 2 : nouvelle commande avec référence manuelle
        sql = """
            INSERT INTO "order" (reference_order, creation_datetime, order_status, order_type)
            VALUES (?, ?, ?::orderStatus, ?::Ordertype)
            RETURNING id, reference_order
        """;

    } else {
        // CAS 3 : commande existante → UPDATE (bloqué si DELIVERED)
        sql = """
            UPDATE "order"
            SET creation_datetime = ?,
                order_status = ?::orderStatus,
                order_type = ?::Ordertype
            WHERE reference_order = ?
              AND order_status <> 'DELIVERED'
            RETURNING id, reference_order
        """;
    }

    // 3️⃣ Exécution
    try (Connection conn = new DBConnection().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        conn.setAutoCommit(false);

        // 4️⃣ Paramètres SQL
        if (!hasReference) {
            // cas 1
            ps.setTimestamp(1, Timestamp.from(orderToSave.getCreationDatetime()));
            ps.setString(2, orderToSave.getOrderStatus().name());
            ps.setString(3, orderToSave.getOrderType().name());

        } else if (!existsInDb) {
            // cas 2
            ps.setString(1, orderToSave.getReference());
            ps.setTimestamp(2, Timestamp.from(orderToSave.getCreationDatetime()));
            ps.setString(3, orderToSave.getOrderStatus().name());
            ps.setString(4, orderToSave.getOrderType().name());

        } else {
            // cas 3
            ps.setTimestamp(1, Timestamp.from(orderToSave.getCreationDatetime()));
            ps.setString(2, orderToSave.getOrderStatus().name());
            ps.setString(3, orderToSave.getOrderType().name());
            ps.setString(4, orderToSave.getReference());
        }

        // 5️⃣ Résultat
        try (ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) {
                throw new RuntimeException("Commande déjà livrée ou inexistante");
            }

            orderToSave.setId(rs.getInt("id"));
            orderToSave.setReference(rs.getString("reference_order"));
        }

        conn.commit();
        return findOrderById(orderToSave.getId());

    } catch (SQLException e) {
        throw new RuntimeException("Erreur lors de l'enregistrement de la commande", e);
    }
}


    private Order findOrderById(Integer orderId) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        String findOrder = """
        select id,
               reference_order,
               creation_datetime,
               order_type,
               order_status
        from "order"
        where id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(findOrder)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setReference(rs.getString("reference_order"));
                order.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
                order.setDishOrders(findDishOrderByIdOrder(orderId));

                // --- Gestion du type et du statut ---
                String typeStr = rs.getString("order_type");
                order.setOrderType(typeStr != null ? OrderType.valueOf(typeStr) : null);

                String statusStr = rs.getString("order_status");
                order.setOrderStatus(statusStr != null ? OrderStatus.valueOf(statusStr) : null);

                return order;
            }


            throw new RuntimeException("Order not found " + orderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbConnection.closeConnection(connection);
        }
    }


    private List<DishOrder> findDishOrderByIdOrder(Integer orderId) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        String findSql = """
                select id, id_dish, quantity from dishorder where dishorder.id_order = ?
                """;
        List<DishOrder> dishOrderList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findSql);
            preparedStatement.setInt(1, orderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Dish dish = findDishById(rs.getInt("id_dish"));
                DishOrder dishOrder = new DishOrder();
                dishOrder.setDish(dish);
                dishOrder.setId(rs.getInt("id"));
                dishOrder.setQuantity(rs.getInt("quantity"));
                dishOrderList.add(dishOrder);
            }
            dbConnection.closeConnection(connection);
            return dishOrderList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    Order findOrderByReference(String reference) {
        DBConnection dbConnection = new DBConnection();
        try (Connection connection = dbConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("""
            select id,
                   reference_order,
                   creation_datetime,
                   order_type,
                   order_status
            from "order"
            where reference_order like ?
        """);

            preparedStatement.setString(1, reference);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Order order = new Order();
                Integer idOrder = resultSet.getInt("id");

                order.setId(idOrder);
                order.setReference(resultSet.getString("reference_order"));
                order.setCreationDatetime(
                        resultSet.getTimestamp("creation_datetime").toInstant()
                );

                // 🔹 Nouveaux attributs
                order.setOrderType(
                        OrderType.valueOf(resultSet.getString("order_type"))
                );
                order.setOrderStatus(
                        OrderStatus.valueOf(resultSet.getString("order_status"))
                );

                order.setDishOrders(findDishOrderByIdOrder(idOrder));
                return order;
            }

            throw new RuntimeException("Order not found with reference " + reference);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
public StockValue getStockValueat(Instant instant, Integer ingredientIdentifier){
  String selectStock = """
          SELECT id_ingredient,unit,
              COALESCE(SUM(
                               CASE
                                   WHEN type = 'IN'  THEN quantity
                                   WHEN type = 'OUT' THEN -quantity
                                   END
                       ), 0) AS actual_quantity
          
          FROM StockMovement
          WHERE   id_ingredient = ? and  creation_datetime <=?
          GROUP BY id_ingredient,unit;
          """;
  DBConnection dbconnection = new DBConnection();
  Connection connection = dbconnection.getConnection();

  try{
      PreparedStatement ps = connection.prepareStatement(selectStock);
      ps.setInt(1,ingredientIdentifier);
      ps.setTimestamp(2,Timestamp.from(instant));
        ResultSet rs = ps.executeQuery();
        StockValue stockValue = new StockValue();
        if(rs.next()){

            stockValue.setQuantity(rs.getDouble("actual_quantity"));
            stockValue.setUnit(Unit.valueOf(rs.getString("unit")));
        }

        dbconnection.closeConnection(connection);
      return stockValue;

  } catch (SQLException e) {
      throw new RuntimeException(e);
  }


}
    Double getDishCost(Integer dishId){
    String selectPrice = """
            SELECT sum(i.price ) as total_price from dishingredient join dish on dish.id = dishingredient.id_dish  join ingredient i on dishingredient.id_ingredient = i.id where dishingredient.id_dish = ?;
            """;
    DBConnection dbConnection = new DBConnection();
    Connection connection = dbConnection.getConnection();
    Double totalPrice =0.0;
    try{
        PreparedStatement ps = connection.prepareStatement(selectPrice);
        ps.setInt(1,dishId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            totalPrice = rs.getDouble("total_price");
        }
        dbConnection.closeConnection(connection);
        return totalPrice;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
    Double  getGrossMargin(Integer dishId){
    String marginSQL = """
            SELECT d.selling_price - sum(i.price * di.quantity_required) as marge
             from DishIngredient di 
             join dish d on d.id = di.id_dish 
             join ingredient i on di.id_ingredient = i.id
              where id_dish = ?  group by d.selling_price ;
            """;
    DBConnection dbConnection = new DBConnection();
    Connection connection = dbConnection.getConnection();
    Double grossMargin =0.0;
    try{
        PreparedStatement ps = connection.prepareStatement(marginSQL);
        ps.setInt(1,dishId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            grossMargin = rs.getDouble("marge");
        }
        dbConnection.closeConnection(connection);
        return grossMargin;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

}
//    public List<Ingredient> createIngredients(List<Ingredient> newIngredients) {
//        if (newIngredients == null || newIngredients.isEmpty()) {
//            return List.of();
//        }
//        List<Ingredient> savedIngredients = new ArrayList<>();
//        DBConnection dbConnection = new DBConnection();
//        Connection conn = dbConnection.getConnection();
//        try {
//            conn.setAutoCommit(false);
//            String insertSql = """
//                        INSERT INTO ingredient (id, name, category, price, required_quantity)
//                        VALUES (?, ?, ?::ingredient_category, ?, ?)
//                        RETURNING id
//                    """;
//            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
//                for (Ingredient ingredient : newIngredients) {
//                    if (ingredient.getId() != null) {
//                        ps.setInt(1, ingredient.getId());
//                    } else {
//                        ps.setInt(1, getNextSerialValue(conn, "ingredient", "id"));
//                    }
//                    ps.setString(2, ingredient.getName());
//                    ps.setString(3, ingredient.getCategory().name());
//                    ps.setDouble(4, ingredient.getPrice());
//                    if (ingredient.getQuantity() != null) {
//                        ps.setDouble(5, ingredient.getQuantity());
//                    }else {
//                        ps.setNull(5, Types.DOUBLE);
//                    }
//
//                    try (ResultSet rs = ps.executeQuery()) {
//                        rs.next();
//                        int generatedId = rs.getInt(1);
//                        ingredient.setId(generatedId);
//                        savedIngredients.add(ingredient);
//                    }
//                }
//                conn.commit();
//                return savedIngredients;
//            } catch (SQLException e) {
//                conn.rollback();
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            dbConnection.closeConnection(conn);
//        }
//    }
//
//




