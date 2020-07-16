<?php
include_once("HessianPHP_v2.0.3/src/HessianService.php");

$conn = new mysqli("127.0.0.1", "root", "8135", "library");
function convert($result) {
    $data = "";
    if(!$result) return $data;

    while($row = $result->fetch_row()) {
        for($i = 0; $i< count($row); $i++){
            $data = $data . $row[$i] . " ";
        }
        $data = $data . "\n";
    }
    return $data;
}

class HessianServer {
    function ping () {
        return "Success";
    }

    function list() {
        if(func_num_args() == 0) return "There are multiple arguments to pass";

        global $conn;
        $sql= "select * from library." . func_get_arg(0);
        return convert($conn->query($sql));
    }
    function search() {
        if(func_num_args() == 0) return "There are multiple arguments to pass";

        global $conn;
        $sql = "select * from library." . func_get_arg(0) . " where " . func_get_arg(1) . " like '%" . func_get_arg(2) . "%'";
        return convert($conn->query($sql));
    }

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
        $success = $conn->query($sql);

        if ($success != -1) {
            return "Success";
        }else{
            return "Fail";
        }
    }

    function update() {
        if(func_num_args() != 4) return "There are multiple arguments to pass";
        global $conn;

        $sql = "update library." . func_get_arg(0) . " set " . func_get_arg(2) . " = " . '"'. func_get_arg(3) . '"' . " where Id = " . func_get_arg(1);
        $success = $conn->query($sql);
        echo $success;
        if ($success != -1) {
            return "Success";
        }else{
            return "Fail";
        }
    }

    function delete() {
        if(func_num_args() != 2) return "There are multiple arguments to pass";
        global $conn;
        $sql = "delete from library." . func_get_arg(0) . " where Id = " . func_get_arg(1);
        $success = $conn->query($sql);
        echo $success;

        if ($success != -1) {
            return "Success";
        }else{
            return "Fail";
        }
    }

  
}

$service = new HessianService(new HessianServer());
$service->handle();
?>