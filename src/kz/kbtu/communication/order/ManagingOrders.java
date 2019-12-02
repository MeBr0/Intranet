package kz.kbtu.communication.order;

import java.util.List;

public interface ManagingOrders {
    List<Order> getOrders(OrderStatus status);
    void addOrder(Order order);
    void changeOrderStatus(Order order, OrderStatus status);
}
