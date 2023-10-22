package models;

public abstract class ModelInterface {
    public abstract void constructFromSQL(java.sql.ResultSet rs) throws java.sql.SQLException;
}