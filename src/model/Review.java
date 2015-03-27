package model;

public class Review {
    private float rating;
    private String reviewContent;

    public Review() {}

    public Review(float rating, String reviewContent) {
        this.rating = rating;
        this.reviewContent = reviewContent;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
