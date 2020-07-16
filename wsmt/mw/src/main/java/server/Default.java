package server;

import com.caucho.hessian.server.HessianServlet;

import mysql.MySql;
import server.DefaultInterface;

public class Default extends HessianServlet implements DefaultInterface {
    private static final long serialVersionUID = 1L;
    private MySql mySQL = new MySql();

    public String ping() {
        try {
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String search(String tableName, String column, String search) {
        return mySQL.list(tableName, column, search);
    }

    public String list(String tableName) {
        return mySQL.list(tableName);
    }

    public String update(String tableName, Integer id, String column, String value) {
        Integer success = mySQL.update(tableName, id, column, value);
        if (success != -1) {
            return "Success";
        } else {
            return "Failed";
        }
    }

    public String create(String tableName, String... args) {
        Integer success = mySQL.create(tableName, args);
        if (success != -1) {
            return "Success";
        } else {
            return "Failed";
        }
    }

    public String delete(String tableName, Integer id) {
        Integer success = mySQL.delete(tableName, id);
        if (success != -1) {
            return "Success";
        } else {
            return "Failed";
        }
    }
}
