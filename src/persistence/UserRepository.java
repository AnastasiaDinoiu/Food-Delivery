package persistence;

import domain.Address;
import domain.Order;
import domain.User;

import java.sql.*;
import java.util.*;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class UserRepository {

    private static final String INSERT_USER_SQL = "INSERT INTO userr (name, county, city, street, " +
            "phone_number, username, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_SQL = "SELECT * FROM userr";
    private static final String UPDATE_USER_SQL = "UPDATE userr SET name = ?, county = ?, city = ?, street = ?" +
            ", phone_number = ?, email = ?, password = ? WHERE username = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM userr WHERE username = ?";

    private final Connection connection;

    private static volatile UserRepository instance;

    private UserRepository() {
        this.connection = getDatabaseConnection();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public User save(User entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress().getCounty());
            preparedStatement.setString(3, entity.getAddress().getCity());
            preparedStatement.setString(4, entity.getAddress().getStreet());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getUsername());
            preparedStatement.setString(7, entity.getEmail());
            preparedStatement.setString(8, entity.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public List<User> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<User> users = new ArrayList<>();
        User userBuffer = Singleton.getInstance(User.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_USER_SQL);
            while (result.next()) {
                userBuffer.setUserID(result.getInt("user_id"));
                userBuffer.setName(result.getString("name"));
                String county = result.getString("county");
                String city = result.getString("city");
                String street = result.getString("street");
                userBuffer.setAddress(new Address(county, city, street));
                userBuffer.setPhoneNumber(result.getString("phone_number"));
                userBuffer.setUsername(result.getString("username"));
                userBuffer.setEmail(result.getString("email"));
                userBuffer.setPassword(result.getString("password"));
                users.add(new User(userBuffer));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> findById(String id) throws InstantiationException, IllegalAccessException {

        List<User> users = findAll();
        for (User user : users) {
            if (user.getUsername().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void update(User entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress().getCounty());
            preparedStatement.setString(3, entity.getAddress().getCity());
            preparedStatement.setString(4, entity.getAddress().getStreet());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getEmail());
            preparedStatement.setString(7, entity.getPassword());
            preparedStatement.setString(8, entity.getUsername());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String username) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
