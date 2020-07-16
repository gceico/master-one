package mysql;

import java.sql.*;

public class MySql {
  private Connection connect = null;

  public String list(String tableName) {
    try {
      Statement s = connect.createStatement();
      ResultSet result = s.executeQuery("select * from library." + tableName);
      return convert(result);
    } catch (Exception e) {

      System.out.println(e.getStackTrace());
      return e.getMessage();
    }

  }

  public String list(String tableName, String column, String search) {
    try {
      Statement s = connect.createStatement();
      ResultSet result = s
          .executeQuery("select * from library." + tableName + " where " + column + " like '%" + search + "%'");
      return convert(result);
    } catch (Exception e) {

      System.out.println(e.getStackTrace());
      return e.getMessage();
    }

  }

  public Integer create(String tableName, String... args) {
    try {
      Integer key = (int) (Math.random() * 1000);
      String values = " values (" + key + ", ";
      for (int i = 0; i < args.length; i++)
        values += "?" + (i != args.length - 1 ? ", " : ")");
      PreparedStatement ps = connect.prepareStatement("insert into  library." + tableName + values);
      for (int i = 0; i < args.length; i++)
        ps.setString(i + 1, args[i]);
      return ps.executeUpdate();

    } catch (Exception e) {
      System.out.println(e.getStackTrace());
      return -1;
    }
  }

  public Integer update(String tableName, Integer id, String column, String value) {
    try {
      PreparedStatement ps = connect
          .prepareStatement("update library." + tableName + " set " + column + " = ? where id = ?");
      ps.setString(1, value);
      ps.setInt(2, id);

      return ps.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.toString());
      return -1;
    }
  }

  public Integer delete(String tableName, Integer id) {
    try {
      PreparedStatement ps = connect.prepareStatement("delete from library." + tableName + " where id = ?");
      ps.setInt(1, id);
      return ps.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.toString());
      return -1;
    }
  }

  public MySql() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connect = DriverManager.getConnection("jdbc:mysql://localhost/library?serverTimezone=UTC", "root", "8135");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  private String convert(ResultSet result) {
    String stringResult = "[";
    try {
      while (result.next()) {
        int columnCount = result.getMetaData().getColumnCount();

        stringResult += "{";
        for (int i = 1; i <= columnCount; i++) {
          String key = result.getMetaData().getColumnName(i);
          String value = result.getString(i);
          stringResult += "\"" + key + "\"" + ":" + "\"" + value + "\"" + (i != columnCount ? ", " : "");
        }
        stringResult += !result.isLast() ? "},\n" : "}\n";

      }
      stringResult += "]";
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return stringResult;
  }

  public void close() {
    try {
      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

}