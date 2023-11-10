package repository;

import database.DBInterface;
import database.DBInstance;
import models.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionRepo extends RepoInterface<Subscription> {
    private static SubscriptionRepo instance;

    protected SubscriptionRepo(DBInterface db, String tableName) {
        super(db, tableName);
    }

    public static SubscriptionRepo getInstance() {
        if (instance == null) {
            instance = new SubscriptionRepo(
                    DBInstance.getInstance(),
                    "subscriptions");
        }
        return instance;
    }

    @Override
    public List<Subscription> findAll() throws SQLException {
        List<Subscription> result = new ArrayList<>();

        Statement stmt = this.db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
        while (rs.next()) {
            Subscription subscription = new Subscription();
            subscription.constructFromSQL(rs);
            result.add(subscription);
        }
        return result;
    }

    public Subscription findById(int userId, int albumId) {
        try {
            Statement stmt = this.db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName + " WHERE user_id = " + userId + " AND album_id = " + albumId + " LIMIT 1");
    
            if (rs.next()) {
                // If a result is found, construct a Subscription object and return it
                Subscription result = new Subscription();
                result.constructFromSQL(rs);
                return result;
            }
    
            // No matching subscription found
            return null;
    
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> findUserByAlbumId(int albumId) {
        List<Integer> userIds = new ArrayList<>();

        try {
            Statement stmt = this.db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT user_id FROM " + this.tableName + " WHERE album_id = " + albumId);
    
            if (rs.next()) {
                // Add each user_id to the list
                int userId = rs.getInt("user_id");
                userIds.add(userId);
            }
    
            // Close the ResultSet and Statement to free up resources
            rs.close();
            stmt.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userIds.isEmpty() ? null : userIds;
    }     

    @Override
    public Subscription create(Subscription subscription) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        int rs = stmt.executeUpdate("INSERT INTO " + this.tableName + " (user_id, album_id) VALUES (" + subscription.getUserId() + ", " + subscription.getAlbumId() + ")");
        if (rs > 0) {
            return this.findById(subscription.getUserId(), subscription.getAlbumId());
        }
        return null;
    }

    @Override
    public Subscription update(Subscription subscription) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        System.out.println(subscription.getStatus());
        System.out.println("UPDATE " + this.tableName + " SET status = '" + subscription.getStatus().toString() + "' WHERE user_id = " + subscription.getUserId() + " AND album_id = " + subscription.getAlbumId());
        int rs = stmt.executeUpdate(
            "UPDATE " + this.tableName + 
            " SET status = '" + subscription.getStatus().toString() + 
            "' WHERE user_id = " + subscription.getUserId() + " AND album_id = " + subscription.getAlbumId());
        if (rs > 0) {
            return this.findById(subscription.getUserId(), subscription.getAlbumId());
        }
        return null;
    }

    @Override
    public Subscription delete(Subscription subscription) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        int rs = stmt.executeUpdate("DELETE FROM " + this.tableName + " WHERE user_id = " + subscription.getUserId() + " AND album_id = " + subscription.getAlbumId());
        if (rs > 0) {
            return subscription;
        }
        return null;
    }

    public List<Subscription> findByStatus(Subscription.SubscriptionStatus status) throws SQLException {
        List<Subscription> result = new ArrayList<>();

        Statement stmt = this.db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName + " WHERE status = '" + status.toString() + "'");
        while (rs.next()) {
            Subscription subscription = new Subscription();
            subscription.constructFromSQL(rs);
            result.add(subscription);
        }
        return result;
    }
}