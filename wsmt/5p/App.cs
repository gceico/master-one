using System;
using System.IO;
using System.Collections;

namespace wsmt_1
{
  class Program
  {
    public static int rows, cols;
    public static String[,] matrix;
    public static ArrayList parsedLines = new ArrayList();

    public static void ParseMatrix(String name)
    {
      using (StreamReader fileReader = new StreamReader(name))
      {
        int i = 0;
        while (fileReader.Peek() >= 0)
        {
          String line = fileReader.ReadLine();
          if (i == 0) rows = Int32.Parse(line);
          if (i == 1) cols = Int32.Parse(line);
          if (i > 1)
          {
            String[] v = line.Split(' ');
            parsedLines.Add(v);
          }
          i++;
        }

        matrix = new String[rows, cols];
        for (int k = 0; k < parsedLines.Count; k++)
        {
          String[] eachLine = (String[])parsedLines[k];
          int l = Int32.Parse(eachLine[0]);
          int j = Int32.Parse(eachLine[1]);
          matrix[l, j] = eachLine[2];
        }
      }
    }
    public static Boolean isGreater(int source, int dest)
    {
      for (int j = 0; j < cols; j++)
      {
        int compare = String.Compare(matrix[source, j], matrix[dest, j]);
        if (compare >= 0)
        {
          return true;
        }
      }
      return false;
    }

    public static void swap(int source, int dest)
    {
      for (int k = 0; k < cols; k++)
      {
        String aux = matrix[source, k];
        matrix[source, k] = matrix[dest, k];
        matrix[dest, k] = aux;
      }

    }

    public static void printMatrix()
    {
      for (int i = 0; i < rows; i++)
      {
        for (int j = 0; j < cols; j++)
        {
          Console.Write(matrix[i, j] + " ");
        }
        Console.WriteLine();
      }
    }
    static void Main(string[] args)
    {
      Console.WriteLine("File name: ");
      String name = Console.ReadLine();
      ParseMatrix(name);

      for (int i = 0; i < rows - 1; i++)
      {
        for (int j = 0; j < rows - i - 1; j++)
        {
          if (isGreater(j, j + 1))
          {
            swap(j, j + 1);
            break;
          }
        }
      }
      printMatrix();
    }
  }
}
