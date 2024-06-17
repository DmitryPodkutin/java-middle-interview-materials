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