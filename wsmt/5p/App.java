
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class App {
  public static int rows, cols;
  public static String[][] matrix;
  public static ArrayList<String[]> parsedLines = new ArrayList<String[]>();

  public static boolean isGreater(int source, int dest) {
    for (int j = 0; j < cols; j++) {
      int compare = matrix[source][j].compareTo(matrix[dest][j]);
      if (compare >= 0) {
        return true;
      }
    }
    return false;
  }

  public static void swap(int source, int dest) {
    String[] aux = matrix[source];
    matrix[source] = matrix[dest];
    matrix[dest] = aux;
  }

  public static void parseMatrix(String name) throws FileNotFoundException {
    int i = 0;
    File file = new File(name);
    Scanner fileReader = new Scanner(file);
    while (fileReader.hasNextLine()) {
      String content = fileReader.nextLine();
      if (i == 0)
        rows = Integer.parseInt(content);
      if (i == 1)
        cols = Integer.parseInt(content);
      if (i > 1) {
        String[] v = content.split("\\s+");
        parsedLines.add(v);
      }
      i++;
    }

    matrix = new String[rows][cols];
    for (int k = 0; k < parsedLines.size(); k++) {
      int l = Integer.parseInt(parsedLines.get(k)[0]);
      int j = Integer.parseInt(parsedLines.get(k)[1]);
      matrix[l][j] = parsedLines.get(k)[2];
    }

    fileReader.close();
  }

  public static void printMatrix() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static void main(String args[]) throws IOException {
    BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("File name: ");
    String name = cin.readLine();
    parseMatrix(name);
    
    for (int i = 0; i < rows - 1; i++) {
      for (int j = 0; j < rows - i - 1; j++) {
        if (isGreater(j, j + 1)) {
          swap(j, j + 1);
          break;
        }
      }
    }

    printMatrix();
  }
}