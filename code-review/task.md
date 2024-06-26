##   Рефакторинг кода (OrderProcessor)
````java
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
````

<details>
<summary>Решение</summary>

````java
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
````
</details>

