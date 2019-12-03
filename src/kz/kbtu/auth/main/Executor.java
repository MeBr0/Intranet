package kz.kbtu.auth.main;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.communication.order.ManagingOrders;
import kz.kbtu.communication.order.Order;
import kz.kbtu.communication.order.OrderStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Executor extends Employee implements ManagingOrders, Serializable {
    public List<Order> orders;

    {
        orders = new ArrayList<>();
    }

    Executor(int salary, String login, String firstName, String lastName) {
        super(salary, login, firstName, lastName);
    }

    @Override
    public List<Order> getOrders(OrderStatus status) {
        List<Order> orders = new ArrayList<>();

        for (Order order: this.orders) {
            if (order.getStatus() == status) {
                orders.add(order);
            }
        }

        return orders;
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void changeOrderStatus(Order order, OrderStatus status) {
        order.setStatus(status);

        if (status == OrderStatus.FINISHED) {
            Employee sender = order.getSender();

            sendMessage(order.getTitle(), "Order finished!", sender);
        }
    }

    @Override
    public String toString() {
        return String.format("Executor: { %s }", super.toString());
    }
}
