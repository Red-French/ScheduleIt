<?php
    // $con = mysqli_connect("my_host", "my_user", "my_password", "my_database");
    $con = mysqli_connect("localhost", "id1157462_unclefatty", "000webhost2017", "id1157462_scheduler");

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

    echo json_encode($response);  // gets turned into JSONobject in RegisterActivity.java
?>
