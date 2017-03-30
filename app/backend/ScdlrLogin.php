<?php
    // $con = mysqli_connect("my_host", "my_user", "my_password", "my_database");
    $con = mysqli_connect("localhost", "id1157462_unclefatty", "000webhost2017", "id1157462_scheduler");

    $username = $_POST["username"];  // Android app will pass username and password
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "SELECT * FROM auth WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $name, $username, $password);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["name"] = $name;
        $response["username"] = $username;
        $response["password"] = $password;
    }

    echo json_encode($response);
?>
