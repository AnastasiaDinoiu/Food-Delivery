package persistence;

import domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class ProductRepository {

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (restaurant_id, name, price) VALUES (?, ?, ?)";
    private static final String SELECT_PRODUCT_SQL = "SELECT * FROM product";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE name = ?";

    private final Connection connection;

    private static volatile ProductRepository instance;

    private ProductRepository() {
        this.connection = getDatabaseConnection();
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            synchronized (ProductRepository.class) {
                if (instance == null) {
                    instance = new ProductRepository();
                }
            }
        }
        return instance;
    }

    public Product save(Product entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            preparedStatement.setInt(1, entity.getRestaurantID());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public List<Product> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<Product> products = new ArrayList<>();
        Product productBuffer = Singleton.getInstance(Product.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_PRODUCT_SQL);
            while (result.next()) {
                productBuffer.setProductID(result.getInt("product_id"));
                productBuffer.setRestaurantID(result.getInt("restaurant_id"));
                productBuffer.setName(result.getString("name"));
                productBuffer.setPrice(result.getDouble("price"));
                products.add(new Product(productBuffer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Optional<Product> findById(String id) throws InstantiationException, IllegalAccessException {

        List<Product> products = findAll();
        for (Product product : products) {
            if (product.getName().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public void delete(String name) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
