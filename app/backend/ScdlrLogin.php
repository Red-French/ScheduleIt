<?php
  include "dbconfig.php";

  $con = mysqli_connect($hostname, $username, $password, $database) or
  trigger_error(mysql_error(),E_USER_ERROR);

  $username = $_POST["username"];  // Android app will pass username and password
  $password = $_POST["password"];

  $statement = mysqli_prepare($con, "SELECT * FROM auth WHERE username = ? AND password = ?");
  mysqli_stmt_bind_param($statement, "ss", $username, $password);
  mysqli_stmt_execute($statement);
  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $userID, $name, $username, $password);

  $response = array();
  $response["Login success"] = false;

  while(mysqli_stmt_fetch($statement)){
      $response["success"] = true;
      $response["name"] = $name;
      $response["username"] = $username;
      $response["password"] = $password;
  }

  echo json_encode($response);

?>
