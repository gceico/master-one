package handlers;

import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.*;

import mysql.MySql;

import java.util.*;

public class CrudHandler implements RequestHandler {
  // Reports the method names of the handled requests
  public String[] handledRequests() {

    return new String[] { "all", "search", "create", "delete", "update" };
  }

  // Processes the requests
  public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
    final MySql mySQL = new MySql();
    String result = "";
    String method = req.getMethod();
    String successMessage = "{\"success\": \"true\"}";
    String failMessage = "{\"fail\": \"true\", \"reasons\": \"Foreign key constraint, resource not found or invalid parameters.\"}";
    String[] params = req.getPositionalParams().stream().toArray(String[]::new);
    String tableName = params[0];
    params = Arrays.copyOfRange(params, 1, params.length);

    switch (method) {
      case "all":
        result = mySQL.list(tableName);
        return new JSONRPC2Response(result, req.getID());
      case "search":
        result = mySQL.list(tableName, params[0], params[1]);
        return new JSONRPC2Response(result, req.getID());
      case "update":
        if (mySQL.update(tableName, Integer.parseInt(params[0]), params[1], params[2]) != -1) {
          return new JSONRPC2Response(successMessage, req.getID());
        } else {
          return new JSONRPC2Response(failMessage, req.getID());
        }
      case "create":
        Integer len = params.length;
        if ((len == 2 && mySQL.create(tableName, params[0], params[1]) != -1)
            || (len == 1 && mySQL.create(tableName, params[0]) != -1)) {
          return new JSONRPC2Response(successMessage, req.getID());
        } else {
          return new JSONRPC2Response(failMessage, req.getID());
        }

      case "delete":
        if (mySQL.delete(tableName, Integer.parseInt(params[0])) != -1) {
          return new JSONRPC2Response(successMessage, req.getID());
        } else {
          return new JSONRPC2Response(failMessage, req.getID());
        }
      default:
        return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
    }
  }
}