import java.math.BigDecimal;
import java.util.Objects;

public class DishIngredient {
    private Integer id;
    private Integer id_dish;
    private Integer id_ingredient;
    private BigDecimal quantity_required;
    private Unit unit_tupe;

    public DishIngredient(Integer id, Integer id_dish, Integer id_ingredient, BigDecimal quantity_required, Unit unit_tupe) {
        this.id = id;
        this.id_dish = id_dish;
        this.id_ingredient = id_ingredient;
        this.quantity_required = quantity_required;
        this.unit_tupe = unit_tupe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_dish() {
        return id_dish;
    }

    public void setId_dish(Integer id_dish) {
        this.id_dish = id_dish;
    }

    public Integer getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(Integer id_ingredient) {
        this.id_ingredient = id_ingredient;
    }

    public BigDecimal getQuantity_required() {
        return quantity_required;
    }

    public void setQuantity_required(BigDecimal quantity_required) {
        this.quantity_required = quantity_required;
    }

    public Unit getUnit_tupe() {
        return unit_tupe;
    }

    public void setUnit_tupe(Unit unit_tupe) {
        this.unit_tupe = unit_tupe;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DishIngredient that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getId_dish(), that.getId_dish()) && Objects.equals(getId_ingredient(), that.getId_ingredient()) && Objects.equals(getQuantity_required(), that.getQuantity_required()) && getUnit_tupe() == that.getUnit_tupe();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getId_dish(), getId_ingredient(), getQuantity_required(), getUnit_tupe());
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                "id=" + id +
                ", id_dish=" + id_dish +
                ", id_ingredient=" + id_ingredient +
                ", quantity_required=" + quantity_required +
                ", unit_tupe=" + unit_tupe +
                '}';
    }
}
