import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Dish {
    private Integer id;
    private Double selling_price;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredient> dishIngredients;
    public Dish() {
    }

    public Dish( Integer id,String name, Double selling_price,DishTypeEnum dishType, List<DishIngredient> dishIngredients) {
        this.dishType = dishType;
        this.id = id;
        this.dishIngredients = dishIngredients;
        this.name = name;
        this.selling_price = selling_price;
    }

    public DishTypeEnum getDishType() {
        return dishType;
    }

    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<DishIngredient> getIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredient> ingredients) {
        this.dishIngredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(Double selling_price) {
        this.selling_price = selling_price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && dishType == dish.dishType && Objects.equals(dishIngredients, dish.dishIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dishType, dishIngredients);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishType=" + dishType +
                ", id=" + id +
                ", selling_price=" + selling_price +
                ", name='" + name + '\'' +
                ", dishingredients=" + dishIngredients+
                '}';
    }
    public Double getDishCost() {
        double totalPrice = 0;
        for (int i = 0; i < dishIngredients.size(); i++) {

            Double quantity = dishIngredients.get(i).getQuantity_required().doubleValue();
            if(quantity == null) {
                throw new RuntimeException("...");
            }
            totalPrice = totalPrice + dishIngredients.get(i).getIngredient().getPrice() * quantity;
        }
        return totalPrice;
    }
    public Double getGrossMargin() {
        if (selling_price == null) {
            throw new RuntimeException("Price is null");
        }
        return selling_price - getDishCost();
    }



}
