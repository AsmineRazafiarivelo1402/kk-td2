public class Main {
    public static void main(String[] args) {
    DataRetriever dataRetriever = new DataRetriever();
    Order order = dataRetriever.findOrderByReference("ORD102");
    order.setOrderType(OrderType.TAKE_AWAY);
    order.setStatusOrder(OrderStatus.DELIVERED);
    dataRetriever.saveOrder(order);


        }}




