package repositories.reviewRepository;



import utils.DatabaseUtil;
import statics.DbConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.RatingAndReview;

public class ReviewRepository implements IReviewRepository{

    public void addRatingAndReview(RatingAndReview ratingAndReview) {
        String sql = "INSERT INTO rating_and_reviews (item_id, customer_id, rating, review, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ratingAndReview.getitemId());
            pstmt.setInt(2, ratingAndReview.getCustomerId());
            pstmt.setInt(3, ratingAndReview.getRating());
            pstmt.setString(4, ratingAndReview.getReview());
            pstmt.setString(5, ratingAndReview.getDate());

            pstmt.executeUpdate();
            System.out.println("Rating and review added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding rating and review: " + e.getMessage());
        }
    }

    public RatingAndReview getRatingAndReview(int id) {
        String sql = "SELECT * FROM rating_and_reviews WHERE id = ?";
        RatingAndReview ratingAndReview = null;

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ratingAndReview = new RatingAndReview(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("rating"),
                    rs.getString("review"),
                    rs.getString("date")
                );
                ratingAndReview.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rating and review: " + e.getMessage());
        }
        return ratingAndReview;
    }

    public RatingAndReview[] getAllRatingAndReviewByItemId(int itemId) {
        String sql = "SELECT * FROM rating_and_reviews WHERE item_id = ?";
        List<RatingAndReview> ratingAndReviews = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RatingAndReview ratingAndReview = new RatingAndReview(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("rating"),
                    rs.getString("review"),
                    rs.getString("date")
                );
                ratingAndReview.setId(rs.getInt("id"));
                ratingAndReviews.add(ratingAndReview);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rating and reviews for item ID: " + itemId + " - " + e.getMessage());
        }
        return ratingAndReviews.toArray(new RatingAndReview[0]);
    }

    public RatingAndReview[] getAllRatingAndReviewByCustomerId(int customerId) {
        String sql = "SELECT * FROM rating_and_reviews WHERE customer_id = ?";
        List<RatingAndReview> ratingAndReviews = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RatingAndReview ratingAndReview = new RatingAndReview(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("rating"),
                    rs.getString("review"),
                    rs.getString("date")
                );
                ratingAndReview.setId(rs.getInt("id"));
                ratingAndReviews.add(ratingAndReview);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rating and reviews for customer ID: " + customerId + " - " + e.getMessage());
        }
        return ratingAndReviews.toArray(new RatingAndReview[0]);
    }

    public RatingAndReview[] getAllRatingAndReviewByCustomerIdAndItemId(int customerId, int itemId) {
        String sql = "SELECT * FROM rating_and_reviews WHERE customer_id = ? AND item_id = ?";
        List<RatingAndReview> ratingAndReviews = new ArrayList<>();

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.setInt(2, itemId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                RatingAndReview ratingAndReview = new RatingAndReview(
                    rs.getInt("item_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("rating"),
                    rs.getString("review"),
                    rs.getString("date")
                );
                ratingAndReview.setId(rs.getInt("id"));
                ratingAndReviews.add(ratingAndReview);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rating and reviews for customer ID: " + customerId + " and item ID: " + itemId + " - " + e.getMessage());
        }
        return ratingAndReviews.toArray(new RatingAndReview[0]);
    }

    public void updateRatingAndReview(RatingAndReview ratingAndReview) {
        String sql = "UPDATE rating_and_reviews SET rating = ?, review = ?, date = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ratingAndReview.getRating());
            pstmt.setString(2, ratingAndReview.getReview());
            pstmt.setString(3, ratingAndReview.getDate());
            pstmt.setInt(4, ratingAndReview.getId());

            pstmt.executeUpdate();
            System.out.println("Rating and review updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating rating and review: " + e.getMessage());
        }
    }

    public void deleteRatingAndReview(int id) {
        String sql = "DELETE FROM rating_and_reviews WHERE id = ?";

        try (Connection conn = DatabaseUtil.connect(DbConfig.DB_CONNECTION_STRING, DbConfig.DB_USER, DbConfig.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Rating and review deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting rating and review: " + e.getMessage());
        }
    }
    
}