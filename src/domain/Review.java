package domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Review implements Cloneable {
    private Integer reviewID;
    private Integer userID;
    private Integer restaurantID;
    private String text;
    private Integer grade;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Review review = new Review();
        review.reviewID = reviewID;
        review.userID = userID;
        review.restaurantID = restaurantID;
        review.text = text;
        review.grade = grade;

        return review;
    }

    public Review() {
    }

    public Review(Integer userID, Integer restaurantID, String text, Integer grade) {
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.text = text;
        this.grade = grade;
    }

    public Review(Review review) {
        this.reviewID = review.reviewID;
        this.userID = review.userID;
        this.restaurantID = review.restaurantID;
        this.text = review.text;
        this.grade = review.grade;
    }

    public static class Builder {
        private Review review = new Review();

        public Builder(Integer userID, Integer restaurantID, Integer grade) {
            review.userID = userID;
            review.restaurantID = restaurantID;
            review.grade = grade;
        }

        public Review.Builder withText(String text) {
            review.text = text;
            return this;
        }

        public Review build() {
            return this.review;
        }
    }

    public Integer getReviewID() {
        return reviewID;
    }

    public void setReviewID(Integer reviewID) {
        this.reviewID = reviewID;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(userID, review.userID) && Objects.equals(restaurantID, review.restaurantID) && Objects.equals(text, review.text) && Objects.equals(grade, review.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, restaurantID, text, grade);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", userID=" + userID +
                ", restaurantID=" + restaurantID +
                ", text='" + text + '\'' +
                ", grade=" + grade +
                '}';
    }
}
