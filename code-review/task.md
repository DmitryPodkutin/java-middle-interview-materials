##   Рефакторинг кода (OrderProcessor)
````java
public class OrderProcessor {

    public Order findOrderById(Long id) {
        return repository.findById(id); 
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

    public double calculateTotalPrice(List<Order> orders) {
        double total = 0.0;
        for (Order order : orders) {
            total += order.getPrice();
        }
        return total;
    }
}

class Order {
    public String status;
    public double price;

    public Order(String status, double price) {
        this.status = status;
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
````

<details>
<summary>Решение</summary>

````java
public class OrderProcessor {

    public Optional<Order> findOrderById(Long id) {
        if (status == null) {
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

    public double calculateTotalPrice(List<Order> orders) {         //общую лоигку вынесли в оттдельный метод
        if (orders == null) {   //проверка на null
            throw new IllegalArgumentException("Orders list cannot be null"); 
        }
        return orders.stream()
            .mapToDouble(Order::getPrice)
            .sum();
    }
}

@Data         // Применение аннотации Lambook вместо написания конструктора и геттеров сеттеров
class Order {
   private OrderStatus status;         //public -> privet , можно сдеалать final
   private double price;
}

public enum OrderStatus {         //вынести в enum (какие преимущества enum перед String?)
    PENDING,
    COMPLETED,
    CANCELLED;  
}
````
</details>

