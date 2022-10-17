package service;

import domain.Order;
import domain.Product;
import exceptions.CustomException;
import persistence.OrderRepository;

import java.util.HashMap;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository = OrderRepository.getInstance();

    public Order registerNewOrder(Integer userID, Integer restaurantID, HashMap<Product, Integer> productsOrdered, Double finalPrice) {
        return orderRepository.save(new Order(userID, restaurantID, productsOrdered, finalPrice));
    }

    public Order registerNewProductsFromOrder(Integer userID, Integer restaurantID, HashMap<Product, Integer> productsOrdered, Double finalPrice) {
        return orderRepository.saveProductsToOrder(new Order(userID, restaurantID, productsOrdered, finalPrice));
    }

    public List<Order> getAllOrders() throws InstantiationException, IllegalAccessException {
        return orderRepository.findAll();
    }

    public void update(Order entity, Integer deliveryManID) throws CustomException, InstantiationException, IllegalAccessException {
        orderRepository.findById(entity.getOrderID())
                .orElseThrow(() -> new CustomException("Cannot update provided entity: " + entity + " It does not exist!"));
        orderRepository.update(entity, deliveryManID);
    }
}
