package domain;

import java.util.HashMap;
import java.util.Objects;

public class Order {
    private Integer orderID;
    private Integer userID;
    private Integer restaurantID;
    private HashMap<Product, Integer> productsOrdered = new HashMap<Product, Integer>();
    private Double finalPrice = 0.0;
    private Integer deliveryManID = 0;

    public Order() {
    }

    public Order(Integer userID, Integer restaurantID, HashMap<Product, Integer> productsOrdered, Double finalPrice) {
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.productsOrdered = productsOrdered;
        this.finalPrice = finalPrice;
    }

    public Order(Order order) {
        this.orderID = order.orderID;
        this.userID = order.userID;
        this.restaurantID = order.restaurantID;
        this.productsOrdered = order.productsOrdered;
        this.finalPrice = order.finalPrice;
        this.deliveryManID = order.deliveryManID;
    }

    public static class Builder {
        private Order order = new Order();

        public Builder(Integer userID, Integer restaurantID) {
            order.userID = userID;
            order.restaurantID = restaurantID;
        }

        public Order.Builder withProducts(Product product, Integer noProducts) {
            order.productsOrdered.put(product, noProducts);
//            order.finalPrice += product.getPrice()*noProducts;
            return this;
        }

        public Order.Builder withProducts(HashMap<Product, Integer> productsOrdered) {
            order.productsOrdered = productsOrdered;
            return this;
        }

        public Order build() {
            return this.order;
        }
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public HashMap<Product, Integer> getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(HashMap<Product, Integer> productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public Double getFinalPrice() {
        return this.finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getDeliveryManID() {
        return deliveryManID;
    }

    public void setDeliveryManID(Integer deliveryManID) {
        this.deliveryManID = deliveryManID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderID, order.orderID) && Objects.equals(userID, order.userID) && Objects.equals(restaurantID, order.restaurantID) && Objects.equals(productsOrdered, order.productsOrdered) && Objects.equals(finalPrice, order.finalPrice) && Objects.equals(deliveryManID, order.deliveryManID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, userID, restaurantID, productsOrdered, finalPrice, deliveryManID);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", userID=" + userID +
                ", restaurantID=" + restaurantID +
                ", productsOrdered=" + productsOrdered +
                ", finalPrice=" + finalPrice +
                ", deliveryManID=" + deliveryManID +
                '}';
    }
}
