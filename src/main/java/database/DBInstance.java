package database;

import utils.ConfigHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInstance implements DBInterface {
    private static DBInterface instance = null;
    private Connection con;

    private final String DB_URL_KEY = "db.url";
    private final String DB_USER_KEY = "db.user";
    private final String DB_PASS_KEY = "db.pass";

    private DBInstance() {
        try {
            // Create a connection with mysql
            ConfigHandler ch = ConfigHandler.getInstance();
            String url = ch.get(DB_URL_KEY);
            String user = ch.get(DB_USER_KEY);
            String pass = ch.get(DB_PASS_KEY);
            System.out.println("Trying to connect to database at " + url + " with user " + user + " and pass " + pass);

            // SQL connection created
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            this.con = DriverManager.getConnection(url, user, pass);
            // Exception handling
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
            System.exit(1); // terminate
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(1); // terminate
        }
    }

    @Override
    public Connection getConnection() {
        System.out.println("Connection: " + this.con);
        return this.con;
    }

    public static DBInterface getInstance() {
        if (instance == null) {
            instance = new DBInstance();
        }

        return instance;
    }

    protected void finalize() throws SQLException {
        this.con.close();
    }
}