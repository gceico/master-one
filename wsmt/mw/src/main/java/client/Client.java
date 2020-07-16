package client;

import com.caucho.hessian.client.*;
import server.*;
import java.util.*;

public class Client {
    Scanner console = new Scanner(System.in);
    Integer choice = null;
    String[] params = {};

    private Client(String urlServ) {
        try {
            HessianProxyFactory hpf = new HessianProxyFactory();
            hpf.setHessian2Request(true);
            DefaultInterface proxy = (DefaultInterface) hpf.create(DefaultInterface.class, urlServ);

            while (true) {
                showMenu();
                Integer[] booksChoices = { 0, 1, 2, 3, 4 };
                String[] choicesMap = { "all", "search", "create", "delete", "update" };
                String tableName = null;

                choice = Integer.parseInt(console.nextLine());

                if (choice != 0 && choice != 5) {
                    params = console.nextLine().split(", ");
                }

                if (Arrays.asList(booksChoices).contains(choice)) {
                    tableName = "_books";
                } else {
                    tableName = "_authors";
                }

                if (choicesMap[choice % 5] == "all")
                    System.out.println(proxy.list(tableName));

                if (choicesMap[choice % 5] == "search")
                    System.out.println(proxy.search(tableName, params[0], params[1]));

                if (choicesMap[choice % 5] == "create")
                    System.out.println(proxy.create(tableName, params));

                if (choicesMap[choice % 5] == "delete")
                    System.out.println(proxy.delete(tableName, Integer.parseInt(params[0], 10)));

                if (choicesMap[choice % 5] == "update")
                    System.out.println(proxy.update(tableName, Integer.parseInt(params[0], 10), params[1], params[2]));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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

    public static void main(String[] args) {
        String url = "http://localhost:8080/";
        System.out.println(url);
        // String url = "http://localhost:8080/HessianServer.php";
        if (args.length != 0) {
            url = args[0];
        }
        new Client(url);
    }
}