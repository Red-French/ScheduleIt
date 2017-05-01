<?php
  include "dbconfig.php";

  $chosenDay = twentyfive;  // get data for this column

  $mysqli = NEW MySQLi($hostname, $username, $password, $database);

  $resultSet = $mysqli->query("SELECT * FROM april");

  $n = 0;

  if ($resultSet->num_rows != 0) {
     while($rows = $resultSet->fetch_assoc( )) {
       $n++;
       $patient = $rows[$chosenDay];  // for each row, get data from column $chosenDay
       $apmtTime = $rows['time'];  // for each row, get the data from column 'time'

      if ($n == 1) {  // PRINT HEADER FIRST TIME THROUGH
        echo "<p>Appointments for date of $chosenDay</p>";
      }

        echo "<p>$apmtTime: $patient</p>";  // PRINT DATA
     }
  } else {
    echo "No results.";
  }

?>
