package persistence;

import domain.*;

import java.sql.*;
import java.util.*;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class RestaurantRepository {

    private static final String INSERT_RESTAURANT_SQL = "INSERT INTO restaurant (name, county, city, street, " +
            "phone_number, restaurant_type, delivery_charge) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_RESTAURANT_SQL = "SELECT * FROM restaurant";
    private static final String UPDATE_RESTAURANT_SQL = "UPDATE restaurant SET county = ?, city = ?, street = ?, " +
            "phone_number = ?, restaurant_type = ?, delivery_charge = ? WHERE name = ?";
    private static final String DELETE_RESTAURANT_SQL = "DELETE FROM restaurant WHERE name = ?";

    private final Connection connection;

    private static volatile RestaurantRepository instance;

    private RestaurantRepository() {
        this.connection = getDatabaseConnection();
    }

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            synchronized (RestaurantRepository.class) {
                if (instance == null) {
                    instance = new RestaurantRepository();
                }
            }
        }
        return instance;
    }

    public Restaurant save(Restaurant entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESTAURANT_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress().getCounty());
            preparedStatement.setString(3, entity.getAddress().getCity());
            preparedStatement.setString(4, entity.getAddress().getStreet());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setObject(6, entity.getRestaurantType());
            preparedStatement.setDouble(7, entity.getDeliveryCharge());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public List<Restaurant> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurantBuffer = Singleton.getInstance(Restaurant.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_RESTAURANT_SQL);
            while (result.next()) {
                restaurantBuffer.setRestaurantID(result.getInt("restaurant_id"));
                restaurantBuffer.setName(result.getString("name"));
                String county = result.getString("county");
                String city = result.getString("city");
                String street = result.getString("street");
                restaurantBuffer.setAddress(new Address(county, city, street));
                restaurantBuffer.setPhoneNumber(result.getString("phone_number"));
                restaurantBuffer.setRestaurantType(result.getString("restaurant_type"));
                restaurantBuffer.setDeliveryCharge(result.getDouble("delivery_charge"));
                restaurants.add(new Restaurant(restaurantBuffer));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public Optional<Restaurant> findById(String id) throws InstantiationException, IllegalAccessException {

        List<Restaurant> restaurants = findAll();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(id)) {
                return Optional.of(restaurant);
            }
        }
        return Optional.empty();
    }

    public Restaurant findById2(String id) throws InstantiationException, IllegalAccessException {

        List<Restaurant> restaurants = findAll();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(id)) {
                return restaurant;
            }
        }
        return null;
    }

    public void update(Restaurant entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESTAURANT_SQL);
            preparedStatement.setString(1, entity.getAddress().getCounty());
            preparedStatement.setString(2, entity.getAddress().getCity());
            preparedStatement.setString(3, entity.getAddress().getStreet());
            preparedStatement.setString(4, entity.getPhoneNumber());
            preparedStatement.setString(5, entity.getRestaurantType());
            preparedStatement.setDouble(6, entity.getDeliveryCharge());
            preparedStatement.setString(7, entity.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String name) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESTAURANT_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
