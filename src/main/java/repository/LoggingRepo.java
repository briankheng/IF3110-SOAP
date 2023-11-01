package repository;

import database.DBInterface;
import database.DBInstance;
import models.Logging;

import java.sql.SQLException;
import java.sql.Statement;

public class LoggingRepo extends RepoInterface<Logging> {
    private static LoggingRepo instance;

    protected LoggingRepo(DBInterface db, String tableName) {
        super(db, tableName);
    }

    public static LoggingRepo getInstance() {
        if (instance == null) {
            instance = new LoggingRepo (
                DBInstance.getInstance(),
                "logging"
            );
        }
        return instance;
    }

    @Override
    public Logging create(Logging log) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement();
        int rs = stmt.executeUpdate("INSERT INTO " + this.tableName + " (description, IP, endpoint, requested_at) VALUES ('" + log.getDescription() + "', '" + log.getIP() + "', '" + log.getEndpoint() + "', '" + log.getRequested_at() + "')");
        if (rs > 0) {
            return log;
        }
        return null;
    }
}