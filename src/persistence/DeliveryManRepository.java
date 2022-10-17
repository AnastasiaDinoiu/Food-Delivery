package persistence;

import domain.Address;
import domain.DeliveryMan;
import domain.User;

import java.sql.*;
import java.util.*;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class DeliveryManRepository {

    private static final String INSERT_DELIV_SQL = "INSERT INTO delivery_man (name, county, city, street, " +
            "phone_number, license_plate) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_DELIV_SQL = "SELECT * FROM delivery_man";
    private static final String UPDATE_DELIV_SQL = "UPDATE delivery_man SET name = ?, county = ?, city = ?, street = ?" +
            ", phone_number = ?, license_plate = ? WHERE delivery_man_id = ?";
    private static final String DELETE_DELIV_SQL = "DELETE FROM delivery_man WHERE delivery_man_id = ?";

    private final Connection connection;

    private static volatile DeliveryManRepository instance;

    private DeliveryManRepository() {
        this.connection = getDatabaseConnection();
    }

    public static DeliveryManRepository getInstance() {
        if (instance == null) {
            synchronized (DeliveryManRepository.class) {
                if (instance == null) {
                    instance = new DeliveryManRepository();
                }
            }
        }
        return instance;
    }

    public DeliveryMan save(DeliveryMan entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DELIV_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress().getCounty());
            preparedStatement.setString(3, entity.getAddress().getCity());
            preparedStatement.setString(4, entity.getAddress().getStreet());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getLicensePlate());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public List<DeliveryMan> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<DeliveryMan> deliveryMen = new ArrayList<>();
        DeliveryMan deliveryManBuffer = Singleton.getInstance(DeliveryMan.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_DELIV_SQL);
            while (result.next()) {
                deliveryManBuffer.setDeliveryManID(result.getInt("delivery_man_id"));
                deliveryManBuffer.setName(result.getString("name"));
                String county = result.getString("county");
                String city = result.getString("city");
                String street = result.getString("street");
                deliveryManBuffer.setAddress(new Address(county, city, street));
                deliveryManBuffer.setPhoneNumber(result.getString("phone_number"));
                deliveryManBuffer.setLicensePlate(result.getString("license_plate"));
                deliveryMen.add(new DeliveryMan(deliveryManBuffer));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveryMen;
    }

    public Optional<DeliveryMan> findById(Integer id) throws InstantiationException, IllegalAccessException {

        List<DeliveryMan> deliveryMen = findAll();
        for (DeliveryMan deliveryMan : deliveryMen) {
            if (deliveryMan.getDeliveryManID().equals(id)) {
                return Optional.of(deliveryMan);
            }
        }
        return Optional.empty();
    }

    public DeliveryMan findById2(Integer id) throws InstantiationException, IllegalAccessException {

        List<DeliveryMan> deliveryMen = findAll();
        for (DeliveryMan deliveryMan : deliveryMen) {
            if (deliveryMan.getDeliveryManID().equals(id)) {
                return deliveryMan;
            }
        }
        return null;
    }

    public void update(DeliveryMan entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DELIV_SQL);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getAddress().getCounty());
            preparedStatement.setString(3, entity.getAddress().getCity());
            preparedStatement.setString(4, entity.getAddress().getStreet());
            preparedStatement.setString(5, entity.getPhoneNumber());
            preparedStatement.setString(6, entity.getLicensePlate());
            preparedStatement.setInt(7, entity.getDeliveryManID());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DELIV_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
