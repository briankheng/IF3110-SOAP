package repository;

import database.DBInterface;
import database.DBInstance;
import models.Favorite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FavoriteRepo extends RepoInterface<Favorite> {
    private static FavoriteRepo instance;

    protected FavoriteRepo(DBInterface db, String tableName) {
        super(db, tableName);
    }

    public static FavoriteRepo getInstance() {
        if (instance == null) {
            instance = new FavoriteRepo(
                    DBInstance.getInstance(),
                    "favorites");
        }
        return instance;
    }

    public List<Integer> findByUserId(int userId) throws SQLException {
        List<Integer> result = new ArrayList<>();

        try {
            Statement stmt = this.db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT album_id FROM " + this.tableName + " WHERE user_id = " + userId);
            while (rs.next()) {
                int albumId = rs.getInt("album_id");
                result.add(albumId);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean find(Favorite favorite) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName + " WHERE user_id = " + favorite.getUserId()
                + " AND album_id = " + favorite.getAlbumId() + " LIMIT 1");

        if (rs.next()) {
            return true;
        }

        return false;
    }

    @Override
    public Favorite create(Favorite favorite) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        stmt.executeUpdate("INSERT INTO " + this.tableName + " (user_id, album_id) VALUES (" + favorite.getUserId()
                + ", " + favorite.getAlbumId() + ")");

        return favorite;
    }

    @Override
    public Favorite delete(Favorite favorite) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM " + this.tableName + " WHERE user_id = " + favorite.getUserId()
                + " AND album_id = " + favorite.getAlbumId());

        return favorite;
    }

    public void deleteByAlbumId(int album_id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        stmt.executeUpdate("DELETE FROM " + this.tableName + " WHERE album_id = " + album_id);
    }
}
