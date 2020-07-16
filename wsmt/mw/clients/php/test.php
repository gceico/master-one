<?php

function create() {
  if(func_num_args() == 0) return "There are multiple arguments to pass";

  global $conn;
  $key = rand(1, 1000);
  $values = " values (" . $key . ", ";
  $params = func_get_arg(1);
  for ($i = 0; $i < count($params) ; $i++) {
      $suffix = "";

      if($i != count($params) - 1){
          $suffix = ", ";
      } else{
          $suffix = ")";
      }

      $values = $values . '"' . $params[$i] . '"' . $suffix;
  }
  
  $sql = "insert into library." . func_get_arg(0) . $values;
  // $success = $conn->query($sql);
  echo $sql;
  if ($success != -1) {
      return "Success";
  }else{
      return "Fail";
  }
}
create("table",["mama", 1 ])
?>