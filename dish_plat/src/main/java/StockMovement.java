import java.time.Instant;
import java.util.Objects;

public class StockMovement {
    int id;
    StockValue value;
    Movementtype movementtype;
    Instant creationDateTime;

    public StockMovement() {
    }

    public StockMovement(int id, Movementtype movementtype,Instant creationDateTime, StockValue value) {
        this.creationDateTime = creationDateTime;
        this.id = id;
        this.movementtype = movementtype;
        this.value = value;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movementtype getMovementtype() {
        return movementtype;
    }

    public void setMovementtype(Movementtype movementtype) {
        this.movementtype = movementtype;
    }

    public StockValue getValue() {
        return value;
    }

    public void setValue(StockValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockMovement that)) return false;
        return getId() == that.getId() && Objects.equals(getValue(), that.getValue()) && getMovementtype() == that.getMovementtype() && Objects.equals(getCreationDateTime(), that.getCreationDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getMovementtype(), getCreationDateTime());
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "creationDateTime=" + creationDateTime +
                ", id=" + id +
                ", value=" + value +
                ", movementtype=" + movementtype +
                '}';
    }
}
