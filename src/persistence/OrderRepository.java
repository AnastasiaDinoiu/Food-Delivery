package persistence;

import domain.Order;
import domain.Product;
import service.ProductService;

import java.sql.*;
import java.util.*;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class OrderRepository {

    private final ProductService productService = new ProductService();

    private static final String INSERT_ORDER_SQL = "INSERT INTO orderr (user_id, restaurant_id, final_price) VALUES (?, ?, ?)";
    private static final String SELECT_ORDER_SQL = "SELECT * FROM orderr";
    private static final String DELETE_ORDER_SQL = "DELETE FROM orderr WHERE order_id = ?";

    private static final String INSERT_ORDER_PROD_SQL = "INSERT INTO order_product (order_id, product_id, amount) VALUES (?, ?, ?)";
    private static final String SELECT_ORDER_PROD_SQL = "SELECT * FROM order_product";

    private static final String SET_DELIV_SQL = "UPDATE orderr SET delivery_man_id = ? WHERE order_id = ?";
    private static final String GET_LAST_ID_SQL = "SELECT order_id FROM orderr ORDER BY order_id DESC LIMIT 1";


    private final Connection connection;

    private static volatile OrderRepository instance;

    private OrderRepository() {
        this.connection = getDatabaseConnection();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                }
            }
        }
        return instance;
    }

    public Order save(Order entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL);
            preparedStatement.setInt(1, entity.getUserID());
            preparedStatement.setInt(2, entity.getRestaurantID());
            preparedStatement.setDouble(3, entity.getFinalPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public Order saveProductsToOrder(Order entity) {

        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(GET_LAST_ID_SQL);
            while (result.next()) {
                entity.setOrderID(result.getInt("order_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Map.Entry mapElem : entity.getProductsOrdered().entrySet()) {
            Product product = (Product) mapElem.getKey();
            Integer noProducts = (Integer) mapElem.getValue();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_PROD_SQL);
                preparedStatement.setInt(1, entity.getOrderID());
                preparedStatement.setInt(2, product.getProductID());
                preparedStatement.setDouble(3, noProducts);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return entity;
    }

    public List<Order> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<Order> orders = new ArrayList<>();
        Order orderBuffer = Singleton.getInstance(Order.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ORDER_SQL);
            while (result.next()) {
                orderBuffer.setOrderID(result.getInt("order_id"));
                orderBuffer.setUserID(result.getInt("user_id"));
                orderBuffer.setRestaurantID(result.getInt("restaurant_id"));
                orderBuffer.setFinalPrice(result.getDouble("final_price"));
                orderBuffer.setDeliveryManID(result.getInt("delivery_man_id"));
                orders.add(new Order(orderBuffer));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Product> products = productService.getAllProducts();
        int order_id, product_id, amount;

        HashMap<Product, Integer> productsOrdered = new HashMap<Product, Integer>();

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ORDER_PROD_SQL);
            while (result.next()) {
                order_id = result.getInt("order_id");
                product_id = result.getInt("product_id");
                amount = result.getInt("amount");
//                System.out.println(order_id + " " + product_id + " " + amount);
                for (Order order : orders) {
                    if (order_id == order.getOrderID()) {
                        productsOrdered = order.getProductsOrdered();
//                        System.out.println(productsOrdered);
                        // !!
                        for (Product product : products) {
                            if (product_id == product.getProductID()) {
                                order.getProductsOrdered().put(product, amount);
                                break;
                            }
                        }
                        break;
                    } else {
                        order.getProductsOrdered().clear();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Optional<Order> findById(Integer id) throws InstantiationException, IllegalAccessException {

        List<Order> orders = findAll();
        for (Order order : orders) {
            if (order.getOrderID().equals(id)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    public void update(Order entity, Integer deliveryManID) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SET_DELIV_SQL);
            preparedStatement.setInt(1, deliveryManID);
            preparedStatement.setInt(2, entity.getOrderID());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
