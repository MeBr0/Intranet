package kz.kbtu.communication.order;

import kz.kbtu.auth.main.Executor;

public interface CreatingOrders {
    Order sendOrder(String title, String text, Executor executor);
}
