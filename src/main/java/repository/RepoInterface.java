package repository;

import database.DBInterface;
import java.util.List;

public abstract class RepoInterface<Model> {
    protected DBInterface db;
    protected String tableName;

    protected RepoInterface(DBInterface db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    // Common database service method
    public List<Model> findAll() throws Exception {
        throw new Exception("Not implemented");
    }

    public Model create(Model model) throws Exception {
        throw new Exception("Not implemented");
    }

    public Model update(Model model) throws Exception {
        throw new Exception("Not implemented");
    }
    
    public Model delete(Model model) throws Exception {
        throw new Exception("Not implemented");
    }
}