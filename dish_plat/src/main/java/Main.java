import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();
//    Order order = dataRetriever.findOrderByReference("ORD103");
//    order.setOrderType(OrderType.TAKE_AWAY);
//  order.setOrderStatus(OrderStatus.DELIVERED);
//    dataRetriever.saveOrder(order);

//StockValue stockValue = dataRetriever.getStockValueat(Instant.now(),1);
//        System.out.println(stockValue);
//        Double price = 0.0;
//        price = dataRetriever.getDishCost(1);
//        System.out.println(price);

        double marge = dataRetriever.getGrossMargin(1);
        System.out.println(marge);

        try {

            List<Map<String, Object>> stats =
                    dataRetriever.getStockStatistics(
                            LocalDate.of(2024, 1, 1),
                            LocalDate.of(2024, 1, 31),
                            "DAY"
                    );

            System.out.println(stats);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        }




