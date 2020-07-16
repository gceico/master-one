<?php
  $name = readline("File name: ");
  $file = fopen($name,"r");
  $parsedLines = array();
  $matrix;
  $cols = 0;
  $rows = 0;

  function parseMatrix() {
    global $file, $content, $rows, $matrix, $parsedLines, $cols, $i;
    $i = 0;

    while(! feof($file))  {
      $content = fgets($file);
      if ($i == 0) $rows = intval($content);
      if ($i == 1) $cols = intval($content);
      if ($i > 1) {
        $v = explode(" ", $content);
        array_push($parsedLines, $v);
      }
      $i++;
    }
    fclose($file);
    $matrix = array_fill(0, $rows, array_fill(0, $cols, "None"));
  
    for ($k = 0; $k < sizeof($parsedLines); $k++) {
      $l = intval($parsedLines[$k][0]);
      $j = intval($parsedLines[$k][1]);
      $matrix[$l][$j] = $parsedLines[$k][2];
    }
  }

  function isGreater($source, $dest) {
    global $matrix, $cols;

    for ($j = 0; $j < $cols; $j++) {
      $compare = strcmp($matrix[$source][$j],($matrix[$dest][$j]));
      if ($compare >= 0) {
        return true;
      }
    }
    return false;
  }

  function swap($source, $dest) {
    global $matrix;
    $aux = $matrix[$source];
    $matrix[$source] = $matrix[$dest];
    $matrix[$dest] = $aux;

  }

  function printMatrix(){
    global $matrix, $rows, $cols;
    for ($i = 0; $i < $rows; $i++) {
      for ($j = 0; $j < $cols; $j++) {
          echo $matrix[$i][$j];
      }
      echo "\n";
    }
  }

  parseMatrix();

  for ($i = 0; $i < $rows - 1; $i++) {
    for ($j = 0; $j < $rows - $i - 1; $j++) {
      if (isGreater($j, $j + 1)) {
        swap($j, $j + 1);
        break;
      }
    }
  }

  printMatrix();
?>