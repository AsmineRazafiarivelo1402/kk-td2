import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Order {
    private  Integer id;
    private String reference;
    private Instant creationDatetime;
    private List<DishOrder> dishOrders;
    private OrderType orderType;
    private StatusOrder statusOrder;
//- Type de la commande : de type enum, sur place (EAT_IN) ou à emporter
//(TAKE_AWAY)
//            - Statut de la commande : de type enum, créé (CREATED), prêt (READY) et livré
//            (DELIVERED)
    public Order() {
    }

    public Order(Instant creationDatetime, List<DishOrder> dishOrders, OrderType orderType, String reference, StatusOrder statusOrder) {
        this.creationDatetime = creationDatetime;
        this.dishOrders = dishOrders;
        this.reference = reference;
        this.orderType = orderType;
        this.statusOrder = statusOrder;
    }

    public Order(Instant creationDatetime, List<DishOrder> dishOrders, Integer id, String reference, StatusOrder statusOrder, OrderType orderType) {
        this.creationDatetime = creationDatetime;
        this.dishOrders = dishOrders;
        this.id = id;
        this.reference = reference;
        this.statusOrder = statusOrder;
        this.orderType = orderType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return getId() == order.getId() && Objects.equals(getReference(), order.getReference()) && Objects.equals(getCreationDatetime(), order.getCreationDatetime()) && Objects.equals(getDishOrders(), order.getDishOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReference(), getCreationDatetime(), getDishOrders());
    }

    @Override
    public String toString() {
        return "Order{" +
                "creationDatetime=" + creationDatetime +
                ", id=" + id +
                ", reference='" + reference + '\'' +

                '}';
    }

    Double getTotalAmountWithoutVAT(){
       throw new RuntimeException("Not Implemented");
    }
    Double getTotalAmountWithVAT(){
        throw new RuntimeException("Not Implemented");
    }
}
