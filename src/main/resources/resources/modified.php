<?php

       $servername = "localhost";
       $username = "root";
       $password = "";
       $dbName = "signup";
       // Create connection
       $conn = new mysqli($servername, $username, $password, $dbname);
       // Check connection
       if ($conn->connect_error) {
           die("Connection failed: " . $conn->connect_error);
       }

        $username = mysqli_real_escape_string($link, $_POST['username']);
        $pass = mysqli_real_escape_string($link, $_POST['pass']);

        $sql = "INSERT INTO 'users' (username, pass) VALUES ('$username', '$pass');";

        if(mysqli_query($conn,$sql){
            echo("Data saved");
     }  else{
            echo("error");
            }
     mysqli_close($conn);


?>