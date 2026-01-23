import java.util.Objects;

public class StockValue {
    Double  quantity;
    Unit unit;

    public StockValue(Double quantity, Unit unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockValue that)) return false;
        return Objects.equals(getQuantity(), that.getQuantity()) && getUnit() == that.getUnit();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getUnit());
    }

    @Override
    public String toString() {
        return "StockValue{" +
                "quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}
