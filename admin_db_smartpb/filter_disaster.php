<?php

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

  $get_days = $_GET['days'];
  $get_days = "-".$get_days." days";

  require_once 'conn.php';
  header('Content-Type: application/json; charset=utf-8');


  $sql = "SELECT * FROM v_disasterlogs_all WHERE";

  foreach($_GET['disaster'] as $index => $value) {
    if($index == 0) {
      $sql = $sql." typeid='$value'";
    } else {
      $sql = $sql." OR typeid='$value'";
    }
  }
  $response = mysqli_query($conn, $sql);

  if ( mysqli_num_rows($response) > 0) {
    $result = array();

    $days= date('Y-m-d h:i:s', strtotime($get_days));

      while( $row = mysqli_fetch_assoc($response))
      {
        if ( $row['eventdate'] > $days) {
          $result[] = $row;
        }
      }
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