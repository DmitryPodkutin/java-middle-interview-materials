import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {

    private final OrderRepository repository;

    public OrderProcessor(OrderRepository repository) {
        this.repository = repository;
    }

    public Order findOrderById(Long id) {
        return repository.findOrderById(id);
    }

    public List<Order> getPendingOrders(List<Order> orders) {
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("PENDING")) {
                pendingOrders.add(order);
            }
        }
        return pendingOrders;
    }

    public List<Order> getCompletedOrders(List<Order> orders) {
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("COMPLETED")) {
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }

    public Double calculateTotalPrice(List<Order> orders) {
        Double total = 0.0;
        for (Order order : orders) {
            total += order.getPrice();
        }
        return total;
    }

    class Order {
        public String status;
        public Double price;

        public Order(String status, Double price) {
            this.status = status;
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }

    public interface OrderRepository{
        Order findOrderById(Long id);
    }
}