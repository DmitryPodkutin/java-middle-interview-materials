import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderProcessor {

    private final OrderRepository repository

    public Optional<Order> findOrderById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }
        return Optional.ofNullable(repository.findById(id));
    }

    public List<Order> getPendingOrders(List<Order> orders) {
        return getOrdersByStatus(orders, OrderStatus.PENDING);
    }

    public List<Order> getCompletedOrders(List<Order> orders) {
        return getOrdersByStatus(orders, OrderStatus.COMPLETED);
    }

    private List<Order> getOrdersByStatus(List<Order> orders, OrderStatus status) {
        if (orders == null) {
            throw new IllegalArgumentException("Orders list cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return orders.stream()         //for в stream
                .filter(order -> status.equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    public Double calculateTotalPrice(List<Order> orders) {         //общую лоигку вынесли в оттдельный метод
        if (orders == null) {   //проверка на null
            throw new IllegalArgumentException("Orders list cannot be null");
        }
        return orders.stream()
                .mapToDouble(Order::getPrice)
                .sum();
    }

    public BigDecimal calculateTotalPriceBigDecimal(List<Order> orders) {  // Использование BigDecimal для суммирования
        if (orders == null) {
            throw new IllegalArgumentException("Orders list cannot be null");
        }
        return orders.stream()
                .map(Order::getPrice)  // Преобразование каждого заказа в BigDecimal цену
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Суммирование всех цен
    }

    public interface OrderRepository{
        Order findOrderById(Long id);
    }

    @Data// Применение аннотации Lambook вместо написания конструктора и геттеров сеттеров
    @AllArgsConstructor
    class Order {
        private final OrderStatus status;         //public -> privet , можно сдеалать final
        private final BigDecimal price;
    }

    public enum OrderStatus {         //вынести в enum (какие преимущества enum перед String?)
        PENDING,
        COMPLETED,
        CANCELLED;
    }
}