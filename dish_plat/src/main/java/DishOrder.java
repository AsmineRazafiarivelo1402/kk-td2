import java.util.Objects;

public class DishOrder {
    private int id;
    private Dish dish;
    private int quantity;

    public DishOrder() {
    }

    public DishOrder(Dish dish, int id, int quantity) {
        this.dish = dish;
        this.id = id;
        this.quantity = quantity;
    }
    public DishOrder(Dish dish,  int quantity) {
        this.dish = dish;

        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DishOrder dishOrder)) return false;
        return getId() == dishOrder.getId() && getQuantity() == dishOrder.getQuantity() && Objects.equals(getDish(), dishOrder.getDish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDish(), getQuantity());
    }

    @Override
    public String toString() {
        return "DishOrder{" +

                ", id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
