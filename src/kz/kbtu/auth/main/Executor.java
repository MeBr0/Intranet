package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.communication.order.ManagingOrders;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Executor extends Employee implements ManagingOrders, Serializable {
    private static List<Order> orders;

    static  {
        orders = new ArrayList<>();
    }

    Executor(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<Order> getOrders(OrderStatus status) {
        List<Order> orders = new ArrayList<>();

        for (Order order: Executor.orders) {
            if (order.getStatus() == status) {
                orders.add(order);
            }
        }

        return orders;
    }

    @Override
    public Order getOrder(String title) {
        for (Order order: orders) {
            if (order.getTitle().equals(title)) {
                return order;
            }
        }

        return null;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public boolean changeOrderStatus(String title, OrderStatus status) {
        for (Order order: orders) {
            if (order.getTitle().equals(title)) {
                order.setStatus(status);

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Executor: { %s }", super.toString());
    }
}
