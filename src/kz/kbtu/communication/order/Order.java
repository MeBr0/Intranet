package kz.kbtu.communication.order;

import kz.kbtu.auth.base.Employee;
import kz.kbtu.communication.message.Message;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Order extends Message implements Serializable {
    private OrderStatus status;

    public Order(OrderStatus status, String title, String text, Employee sender, Date timestamp) {
        super(title, text, sender, timestamp);

        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void print() {
        Employee sender = getSender();

        System.out.println(String.format("Order: %s [%s]", getTitle(), status));
        System.out.println(String.format("Text: %s", getText()));;
        System.out.println(String.format("Sender: %s [%s]", sender.getFullName(), sender.getLogin()));;
        System.out.println(String.format("Date: %s", getTimestamp()));
    }

    @Override
    public String toString() {
        return "Order{" +
                "status=" + status +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
