package service;

import domain.Review;
import persistence.ReviewRepository;

import java.util.List;

public class ReviewService {

    private final ReviewRepository reviewRepository = ReviewRepository.getInstance();

    public Review registerNewReview(Integer userID, Integer restaurantID, String text, Integer grade) {
        return reviewRepository.save(new Review(userID, restaurantID, text, grade));
    }

    public List<Review> getAllReviews() throws InstantiationException, IllegalAccessException {
        return reviewRepository.findAll();
    }
}
