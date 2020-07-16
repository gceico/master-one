using System;
using System.IO;
using System.Net;
using System.Text;
using System.Collections.Generic;
using System.Text.Json;
using System.Text.Json.Serialization;
namespace Client
{
  class RequestModel
  {
    public string id { get; set; }
    public string jsonrpc { get; set; }
    public string method { get; set; }
    public Array _params { get; set; }

    public RequestModel(string id, string jsonrpc, string method, Array _params)
    {
      this.id = id;
      this.jsonrpc = jsonrpc;
      this.method = method;
      this._params = _params;
    }
  }
  class ResponseModel
  {
    public string result { get; set; }
    public string jsonrpc { get; set; }
    public string id { get; set; }
  }
  public class Program
  {
    int requestId = 1;

    public void ShowMenu()
    {
      Console.WriteLine("Select option:");
      Console.WriteLine("---BOOKS---------------------------------");
      Console.WriteLine("0. all books");
      Console.WriteLine("1. search book by [column, value]");
      Console.WriteLine("2. insert book [title, author_id]");
      Console.WriteLine("3. delete book [id]");
      Console.WriteLine("4. patch book [id, column, value]");
      Console.WriteLine("---AUTHORS-------------------------------");
      Console.WriteLine("5. all authors");
      Console.WriteLine("6. search author by [column, value]");
      Console.WriteLine("7. insert author [name]");
      Console.WriteLine("8. delete author [id]");
      Console.WriteLine("9. patch author [id, column, value]");
    }

    static void Main(string[] args)
    {

      while (true)
      {
        Program p = new Program();
        p.ShowMenu();
        string choice = Console.ReadLine();
        string[] _params = { };

        if (choice != "0" & choice != "5")
        {
          Console.WriteLine("Type parameters:");
          string input = Console.ReadLine();
          _params = input.Split(", ");
        }

        if (choice == "0") p.MakeRequest("all", "_books", _params, args[0]);
        if (choice == "1") p.MakeRequest("search", "_books", _params, args[0]);
        if (choice == "2") p.MakeRequest("create", "_books", _params, args[0]);
        if (choice == "3") p.MakeRequest("delete", "_books", _params, args[0]);
        if (choice == "4") p.MakeRequest("update", "_books", _params, args[0]);
        if (choice == "5") p.MakeRequest("all", "_authors", _params, args[0]);
        if (choice == "6") p.MakeRequest("search", "_authors", _params, args[0]);
        if (choice == "7") p.MakeRequest("create", "_authors", _params, args[0]);
        if (choice == "8") p.MakeRequest("delete", "_authors", _params, args[0]);
        if (choice == "9") p.MakeRequest("update", "_authors", _params, args[0]);
      }

    }

      public void MakeRequest(string method, string table, string[] _params, string url)
    {
      requestId++;
      var httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
      httpWebRequest.ContentType = "application/json";
      httpWebRequest.Method = "POST";
      using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
      {
        string[] p = new string[_params.Length + 1];
        p[0] = table;
        for (int i = 0; i < _params.Length; i++)
        {
          p[i + 1] = _params[i];
        }
        var request = new RequestModel(requestId.ToString(), "2.0", method, p);
        string json = JsonSerializer.Serialize<RequestModel>(request);
        json = json.Replace("\"_params\":", "\"params\":");
        streamWriter.Write(json);
      }

      var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
      using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
      {
        var result = streamReader.ReadToEnd();
        var obj = JsonSerializer.Deserialize<ResponseModel>(result);
        Console.WriteLine();
        Console.WriteLine(obj.result);
        Console.WriteLine();
      }
    }
  }
}