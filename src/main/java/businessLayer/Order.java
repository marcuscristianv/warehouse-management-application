package businessLayer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private int clientId;
    private LocalDateTime orderDate;

    public Order() {
        this.orderDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    public Order(int orderId, int clientId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
