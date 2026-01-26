import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataRetriever {
    Dish findDishById(Integer id) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                         
                            select id_dish,dish.id, dish.name, dish.dish_type, dish.selling_price  from DishIngredient join dish on dish.id = id_dish join ingredient on ingredient.id= id_ingredient  where dish.id = ?;
                         
                            """);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id_dish"));
                dish.setName(resultSet.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));

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
    List<Dish> findDishsByIngredientName(String IngredientName){
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
            preparedStatement.setString(1, IngredientName);
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
            throw new RuntimeException("Dish not found " + IngredientName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        List<DishIngredient> findIngredientsByCriteria(String ingredientName, CategoryEnum category, String dishName, int page, int size) {
            if (ingredientName == null || category == null || dishName == null) {
                return new ArrayList<>();
            }
            DBConnection dbconnection = new DBConnection();
            Connection connection = dbconnection.getConnection();
            List<DishIngredient> dishIngredientList = new ArrayList<>();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        """
                             
                                select id_ingredient,id_dish,dish.name, 
                                       ingredient.id AS idIngredient, ingredient.name AS ingredient_name, ingredient.price,ingredient.category , dish.name AS dish_name
                                from DishIngredient 
                                    join dish on dish.id = id_dish 
                                    join ingredient on ingredient.id= id_ingredient
                                where ingredient.name ilike ? or ingredient.category =?::ingredient_category or dish.name ilike ? LIMIT ? OFFSET ? 
                             
                                """);
                preparedStatement.setString(1, ingredientName);
                preparedStatement.setString(2, category.name());
                preparedStatement.setString(3, dishName);
                preparedStatement.setInt(4, page);
                preparedStatement.setInt(5, size);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Ingredient ingredient = new Ingredient();
                    Dish dish = new Dish();
                    DishIngredient dishIngredient = new DishIngredient();
                    ingredient.setId(resultSet.getInt("idIngredient"));
                    ingredient.setName(resultSet.getString("ingredient_name"));
                    dish.setName(resultSet.getString("dish_name"));
                    dishIngredient.setIngredient(ingredient);
                    dishIngredient.setDish(dish);
                    dishIngredientList.add(dishIngredient);
                    return dishIngredientList;
                }else {
                    return dishIngredientList;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

            List<DishIngredient> newIngredients = toSave.getIngredients();
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
                ps.setBigDecimal(4,dishIngredient.getQuantity_required());
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
    Ingredient saveIngredient(Ingredient toSave){
        DBConnection dbconnection =  new DBConnection();
        Connection  conn = dbconnection.getConnection();
        if(toSave == null){
            throw new RuntimeException();
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
            if (toSave.getId() != null) {
                ps.setInt(1, toSave.getId());
            } else {
                ps.setInt(1, getNextSerialValue(conn, "ingredient", "id"));
            }
            ps.setString(2, toSave.getName());
            if (toSave.getPrice() != null) {
                ps.setDouble(3, toSave.getPrice());
            } else {
                ps.setNull(3, Types.DOUBLE);
            }

            ps.setString(4, toSave.getCategory().name());
            ps.execute();
            saveStockMovementList(toSave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }

    private void saveStockMovementList(Ingredient toSave) {
        String insertStockMovement = """
                     INSERT INTO StockMovement (id,id_ingredient ,quantity,type, unit, creation_datetime)
                                    VALUES (?,  ?, ?,?::mouvement_type,?::unit_type,?)
                                    ON CONFLICT (id) DO NOTHING
                                 """;
        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(insertStockMovement)) {
            for (StockMovement stock : toSave.getStockMovementList()) {
                ps.setInt(1,stock.getId());
                ps.setInt(2,toSave.getId());
                ps.setDouble(3,stock.getValue().getQuantity());
                ps.setString(4,stock.getMovementtype().name());
                ps.setString(5,stock.getValue().getUnit().name());
                ps.setString(6,stock.getCreationDateTime().toString());
                ps.executeUpdate(); // ✅ EXECUTE DANS LA BOUCLE
            }
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




