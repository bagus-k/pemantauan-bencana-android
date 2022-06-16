<?php
  require_once 'conn.php';
  $sql = "SELECT * FROM tb_disasterlogs";

  $response = mysqli_query($conn, $sql);

  if ( mysqli_num_rows($response) > 0) {
    $result = array();

    $days= date('Y-m-d h:i:s', strtotime('-30 days'));

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

?>