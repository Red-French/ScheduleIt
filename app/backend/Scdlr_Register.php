<?php
  include "dbconfig.php";

  $con = mysqli_connect($hostname, $username, $password, $database) or
  trigger_error(mysql_error(),E_USER_ERROR);

  $name = $_POST["name"];  // Android wll pass name, age, username, and password
  $username = $_POST["username"];
  $password = $_POST["password"];

  // insert info above into the table
  $statement = mysqli_prepare($con, "INSERT INTO auth (name, username, password) VALUES (?, ?, ?)");
  mysqli_stmt_bind_param($statement, "sss", $name, $username, $password);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["Register success"] = true;

  echo json_encode($response);  // gets turned into JSONobject in RegisterActivity.java
?>
