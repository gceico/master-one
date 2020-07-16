<?php
include_once("HessianPHP_v2.0.3/src/HessianClient.php");

function showMenu() {
  echo "Select option:\n";
  echo "---BOOKS---------------------------------\n";
  echo "0. all books\n";
  echo "1. search book by [column, value]\n";
  echo "2. insert book [title, author_id]\n";
  echo "3. delete book [id]\n";
  echo "4. patch book [id, column, value]\n";
  echo "---AUTHORS-------------------------------\n";
  echo "5. all authors\n";
  echo "6. search author by [column, value]\n";
  echo "7. insert author [name]\n";
  echo "8. delete author [id]\n";
  echo "9. patch author [id, column, value]\n";

}

$url = "http://localhost:8080/";
// $url = "http://localhost:8080/HessianServer.php";
if ($argc > 1) {
  $url = $argv[1];
}

function main() {
  global $url;
 
  $proxy = new HessianClient( $url);

  while(TRUE) {
    showMenu();
    $choice = 0;
    $params = [];
    $tableName = "_books";
    $booksChoices = [0, 1, 2, 3, 4];
    $choicesMap = ["all", "search", "create", "delete", "update"];

    $choice = (int)readline();

    if (in_array($choice, $booksChoices)) {
      $tableName = "_books";
    } else {
      $tableName = "_authors";
    }

    if ($choice != 0 && $choice != 5){
      $params = readline();
      $params = explode(", ", $params);
    }

    try {
      if ($choicesMap[$choice % 5] == "all"){
        echo $proxy->list($tableName) . "\n";
      }
  
      if ($choicesMap[$choice % 5] == "search"){
        echo $proxy->search($tableName, $params[0], $params[1]) . "\n";
      }
      if ($choicesMap[$choice % 5] == "create"){
        echo $proxy->create($tableName, $params) . "\n";
      }
  
      if ($choicesMap[$choice % 5] == "delete"){
        echo $proxy->delete($tableName, (int)$params[0]) . "\n";
      }
  
      if ($choicesMap[$choice % 5] == "update"){
        echo $proxy->update($tableName, (int)$params[0], $params[1], $params[2]) . "\n";
      }
    }catch (Exception $e) {
      echo "";
    }
  }
}

main();