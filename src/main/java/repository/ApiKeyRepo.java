package repository;

import database.DBInterface;
import database.DBInstance;
import models.ApiKey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApiKeyRepo extends RepoInterface<ApiKey> {
    private static ApiKeyRepo instance;

    protected ApiKeyRepo(DBInterface db, String tableName) {
        super(db, tableName);
    }

    public static ApiKeyRepo getInstance() {
        if (instance == null) {
            instance = new ApiKeyRepo (
                DBInstance.getInstance(),
                "api_keys"
            );
        }
        return instance;
    }

    @Override
    public List<ApiKey> findAll() throws SQLException {
        List<ApiKey> result = new ArrayList<>();

        Statement stmt = this.db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
        while (rs.next()) {
            ApiKey subscription = new ApiKey();
            subscription.constructFromSQL(rs);
            result.add(subscription);
        }
        return result;
    }
}