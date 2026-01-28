import java.time.Instant;
import java.util.List;

public class Order {
    private  int id;
    private String reference;
    private Instant creationDatetime;
    private List<DishOrder> dishOrders;

    Double getTotalAmountWithoutVAT(){
        return 0.0;
    }
    Double getTotalAmountWithVAT(){
        return 0.0;
    }
}
