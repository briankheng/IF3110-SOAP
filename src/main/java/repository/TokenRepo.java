package repository;

import database.DBInterface;
import database.DBInstance;
import models.Token;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TokenRepo extends RepoInterface<Token> {
    private static TokenRepo instance;

    protected TokenRepo(DBInterface db, String tableName) {
        super(db, tableName);
    }

    public static TokenRepo getInstance() {
        if (instance == null) {
            instance = new TokenRepo(
                    DBInstance.getInstance(),
                    "token");
        }
        return instance;
    }

    @Override
    public List<Token> findAll() throws SQLException {
        List<Token> result = new ArrayList<>();

        Statement stmt = this.db.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName);
        while (rs.next()) {
            Token token = new Token();
            token.constructFromSQL(rs);
            result.add(token);
        }
        return result;
    }

    public Token findByTokenString(String tokenString) {
        try {
            Statement stmt = this.db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.tableName + " WHERE token_string = '" + tokenString + "' LIMIT 1");
    
            if (rs.next()) {
                // If a result is found, construct a Token object and return it
                Token result = new Token();
                result.constructFromSQL(rs);
                return result;
            }
    
            // No matching token is found
            return null;
    
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}