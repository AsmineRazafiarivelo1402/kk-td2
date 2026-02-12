import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();
//    Order order = dataRetriever.findOrderByReference("ORD103");
//    order.setOrderType(OrderType.TAKE_AWAY);
//  order.setOrderStatus(OrderStatus.DELIVERED);
//    dataRetriever.saveOrder(order);

StockValue stockValue = dataRetriever.getStockValueat(Instant.now(),1);
        System.out.println(stockValue);

        }}




