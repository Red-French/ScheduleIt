<?php
  include "dbconfig.php";

  $con = mysqli_connect($hostname, $username, $password, $database) or
  trigger_error(mysql_error(),E_USER_ERROR);

  $name = $_POST["name"];
  $chosenDay = $_POST["chosenDay"];
  $chosenMonth = $_POST["chosenMonth"];
  $chosenTime = $_POST["chosenTime"];

  // update database with info passed in above
  $sqlString = "UPDATE ".$chosenMonth." SET ".$chosenDay."=? WHERE time=?";
  $statement = mysqli_prepare($con, $sqlString);
  mysqli_stmt_bind_param($statement, "ss", $name, $chosenTime);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);  // gets turned into JSONobject in calling Activity (RegisterActivity, BookingActivity)

?>
