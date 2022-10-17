package comparator;

import domain.Review;

import java.util.Comparator;

public class GradeReviewComparator implements Comparator<Review> {

    @Override
    public int compare(Review r1, Review r2) {
        if(r1.getGrade().equals(r2.getGrade())) {
            return r1.getUserID().compareTo(r2.getUserID());
        }
        return r1.getGrade().compareTo(r2.getGrade());
    }
}
