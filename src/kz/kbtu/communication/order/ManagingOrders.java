package kz.kbtu.communication.order;

import java.util.List;

public interface ManagingOrders {
    List<Order> getOrders();
    List<Order> getOrders(OrderStatus status);
    Order getOrder(String title);
    void addOrder(Order order);
    boolean changeOrderStatus(String title, OrderStatus status);
}
