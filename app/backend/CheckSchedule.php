<?php
  include "dbconfig.php";

  $con = mysqli_connect($hostname, $username, $password, $database);

  if(mysqli_connect_errno()) {
    echo 'Failed to connect to MySQL: ' . mysqli_connect_errno();
  }

  $name = $_POST["name"];
  $chosenDay = $_POST["chosenDay"];
  $chosenMonth = $_POST["chosenMonth"];
  $chosenTime = $_POST["chosenTime"];

  $sqlString = "SELECT ".$chosenDay." FROM ".$chosenMonth." WHERE time=?";
  $statement = mysqli_prepare($con, $sqlString);
  mysqli_stmt_bind_param($statement, "s", $chosenTime);
  mysqli_stmt_execute($statement);

  $results = array();
  $response = array();

  $meta = $statement->result_metadata();

  while ($field = $meta->fetch_field()) {
    $parameters[] = &$row[$field->name];
  }

  call_user_func_array(array($statement, 'bind_result'), $parameters);

  while ($statement->fetch()) {
    foreach($row as $key => $val) {
      $kv_pair[$key] = $val;
    }
    $results = $kv_pair;
// var_dump($results);
    array_filter($results, function($value) {
      global $name;
      if ($value == NULL) {
        $response["available"] = true;
        echo json_encode($response);
        // echo "$name can book this appointment";
        // var_dump($response);
      } else {
        $response["available"] = false;
        echo json_encode($response);
        // echo "Already booked";
        // var_dump($response);
      }
    });

  }

?>
