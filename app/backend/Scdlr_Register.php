<?php
    // $con = mysqli_connect("my_host", "my_user", "my_password", "my_database");
    $con = mysqli_connect("localhost", "id1157462_unclefatty", "000webhost2017", "id1157462_scheduler");

    $name = $_POST["name"];  // Android wll pass name, age, username, and password
    $username = $_POST["username"];
    $password = $_POST["password"];

    // insert info above into the table
    $statement = mysqli_prepare($con, "INSERT INTO auth (name, username, password) VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sss", $name, $username, $password);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);  // gets turned into JSONobject in RegisterActivity.java
?>
