<?php
  include "dbconfig.php";

  // $name = $_POST("name");  // TODO: check for existing appointment on same day
  $chosenDay = $_POST["chosenDay"];
  $chosenMonth = $_POST["chosenMonth"];

  $mysqli = NEW MySQLi($hostname, $username, $password, $database);

  $resultSet = $mysqli->query("SELECT * FROM ".$chosenMonth);

  $n = 0;
  $result = array();

  if ($resultSet->num_rows != 0) {
     while($row = $resultSet->fetch_assoc()) {
       $n++;
       $patient = $row[$chosenDay];  // for each row, get data from column $chosenDay
       $apmtTime = $row['time'];  // for each row, get the data from column 'time'

       array_push($result,array($apmtTime=>$patient));
      // array_push($result[$apmtTime]=$patient);  // prints whole month

      if ($n == 19) {
        $response["success"] = true;
        echo json_encode($result);
      }
    }
  } else {
    $response["success"] = false;
  }

?>
