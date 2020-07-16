package client;

import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.*;

import handlers.CrudHandler;

import java.util.*;

public class Client {
  Integer reqId = 0;
  String[] params = {};
  Integer choice = null;
  Scanner console = new Scanner(System.in);
  Dispatcher dispatcher = new Dispatcher();

  private void showMenu() {
    System.out.println("Select option:");
    System.out.println("---BOOKS---------------------------------");
    System.out.println("0. all books");
    System.out.println("1. search book by [column, value]");
    System.out.println("2. insert book [title, author_id]");
    System.out.println("3. delete book [id]");
    System.out.println("4. patch book [id, column, value]");
    System.out.println("---AUTHORS-------------------------------");
    System.out.println("5. all authors");
    System.out.println("6. search author by [column, value]");
    System.out.println("7. insert author [name]");
    System.out.println("8. delete author [id]");
    System.out.println("9. patch author [id, column, value]");
  }

  class ResponseModel {
    public String result;
    public String jsonrpc;
    public String id;
  }

  private Client() {
    reqId++;
    dispatcher.register(new CrudHandler());

    while (true) {
      showMenu();
      Integer[] booksChoices = { 0, 1, 2, 3, 4 };
      String[] choicesMap = { "all", "search", "create", "delete", "update" };

      List<Object> query = new LinkedList<Object>();

      choice = Integer.parseInt(console.nextLine());

      if (choice != 0 && choice != 5) {
        System.out.println("Type parameters:");
        params = console.nextLine().split(", ");
      }

      if (Arrays.asList(booksChoices).contains(choice)) {
        query.add("_books");
      } else {
        query.add("_authors");
      }

      for (int i = 0; i < params.length; i++) {
        query.add(params[i]);
      }

      JSONRPC2Request req = new JSONRPC2Request(choicesMap[choice % 5], query, reqId.toString());
      JSONRPC2Response res = dispatcher.process(req, null);
      System.out.println(res.getResult());
    }
  }

  public static void main(String[] args) {
    new Client();
  }
}