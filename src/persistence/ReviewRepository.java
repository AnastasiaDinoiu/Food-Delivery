package persistence;

import domain.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static persistence.SQLutil.DatabaseConnectionUtils.getDatabaseConnection;

public class ReviewRepository {

    private static final String INSERT_REVIEW_SQL = "INSERT INTO review (user_id, restaurant_id, text, grade) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_REVIEW_SQL = "SELECT * FROM review";

    private final Connection connection;

    private static volatile ReviewRepository instance;

    private ReviewRepository() {
        this.connection = getDatabaseConnection();
    }

    public static ReviewRepository getInstance() {
        if (instance == null) {
            synchronized (ReviewRepository.class) {
                if (instance == null) {
                    instance = new ReviewRepository();
                }
            }
        }
        return instance;
    }


    public Review save(Review entity) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REVIEW_SQL);
            preparedStatement.setInt(1, entity.getUserID());
            preparedStatement.setInt(2, entity.getRestaurantID());
            preparedStatement.setString(3, entity.getText());
            preparedStatement.setInt(4, entity.getGrade());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public List<Review> findAll() throws InstantiationException, IllegalAccessException {

        ArrayList<Review> reviews = new ArrayList<>();
        Review reviewBuffer = Singleton.getInstance(Review.class);
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_REVIEW_SQL);
            while (result.next()) {
                reviewBuffer.setReviewID(result.getInt("review_id"));
                reviewBuffer.setUserID(result.getInt("user_id"));
                reviewBuffer.setRestaurantID(result.getInt("restaurant_id"));
                reviewBuffer.setText(result.getString("text"));
                reviewBuffer.setGrade(result.getInt("grade"));
                reviews.add(new Review(reviewBuffer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
