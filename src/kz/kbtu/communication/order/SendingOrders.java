package kz.kbtu.communication.order;

import kz.kbtu.auth.main.Executor;

public interface SendingOrders {
    Order sendOrder(String title, String text, Executor executor);
}
