<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $username = $_POST['username'];
    $password = $_POST['password'];

    require_once 'conn.php';

    $sql = "SELECT * FROM tb_users WHERE username='$username' AND password='$password'";

    $response = mysqli_query($conn, $sql);


    if ( mysqli_num_rows($response) > 0) {
        $row = mysqli_fetch_assoc($response);
   $result = array();

      $tmp = array();

       $tmp['email'] = $row['email'];
       $tmp['first_name'] = $row['first_name'];
       $tmp['lastname_name'] = $row['last_name'];

       $result[] = $tmp;

        $status = "1";
        $message = "success";
        echo json_encode(array('status'=>$status, 'message'=>$message, 'data'=>$result), JSON_PRETTY_PRINT);
        mysqli_close($conn);
    }else {
        $status = "0";
        $message = "error";
        echo json_encode(array('status'=>$status, 'message'=>$message), JSON_PRETTY_PRINT);
    }
}

?>