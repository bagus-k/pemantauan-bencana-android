<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

  $idlogs = $_GET['idlogs'];

  require_once 'conn.php';
  header('Content-Type: application/json; charset=utf-8');

  $sql = "SELECT * FROM v_disasterlogs_all WHERE id_logs='$idlogs'";

  $response = mysqli_query($conn, $sql);

  if ( mysqli_num_rows($response) > 0) {
    $result = array();
    $row = mysqli_fetch_assoc($response);
    $result[] = $row;
    $status = "1";
    $message = "success";
    echo json_encode(array('status'=>$status, 'message'=>$message, 'data' => $result), JSON_PRETTY_PRINT);
    mysqli_close($conn);
  } else {
    $status = "0";
    $message = "error";
    echo json_encode(array('status'=>$status, 'message'=>$message), JSON_PRETTY_PRINT);
  }

}
?>