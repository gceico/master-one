package server;

public interface DefaultInterface {
    public String ping();

    public String search(String tableName, String column, String search);

    public String list(String tableName);

    public String update(String tableName, Integer id, String column, String value);

    public String create(String tableName, String... args);

    public String delete(String tableName, Integer id);
}
