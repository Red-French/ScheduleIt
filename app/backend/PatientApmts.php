<?php
  include "dbconfig.php";

  $chosenMonth = $_POST["chosenMonth"];
  $mysqli = NEW MySQLi($hostname, $username, $password, $database);

  $resultSet = $mysqli->query("SELECT * FROM ".$chosenMonth);

  $n = 0;
  $result = array();
  // var_dump("$result");

  if ($resultSet->num_rows != 0) {
     while($row = $resultSet->fetch_assoc()) {
       $n++;
       $result[] = $row;

      if ($n == 19) {
        $response["success"] = true;
        echo json_encode($result);
      }
    }
  } else {
    $response["success"] = false;
  }

?>
