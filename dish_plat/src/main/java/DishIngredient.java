import java.math.BigDecimal;
import java.util.Objects;

public class DishIngredient {
    private Integer id;
    private Dish dish;
    private Ingredient ingredient;


    private double quantity_required;
    private Unit unit_tupe;

    public DishIngredient() {
    }

    public DishIngredient(Integer id, Dish dish, Ingredient ingredient, double quantity_required, Unit unit_tupe) {
        this.dish = dish;
        this.id = id;
        this.ingredient = ingredient;
        this.quantity_required = quantity_required;
        this.unit_tupe = unit_tupe;
    }

    public void setQuantity_required(double quantity_required) {
        this.quantity_required = quantity_required;
    }
    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity_required() {
        return quantity_required;
    }

//    public void setQuantity_required(BigDecimal quantity_required) {
//        this.quantity_required = quantity_required;
//    }

    public Unit getUnit_tupe() {
        return unit_tupe;
    }

    public void setUnit_tupe(Unit unit_tupe) {
        this.unit_tupe = unit_tupe;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DishIngredient that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDish(), that.getDish()) && Objects.equals(getIngredient(), that.getIngredient()) && Objects.equals(getQuantity_required(), that.getQuantity_required()) && getUnit_tupe() == that.getUnit_tupe();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDish(), getIngredient(), getQuantity_required(), getUnit_tupe());
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                ", id=" + id +
                ", ingredient=" + ingredient +
                ", quantity_required=" + quantity_required +
                ", unit_tupe=" + unit_tupe +
                '}';
    }
}
