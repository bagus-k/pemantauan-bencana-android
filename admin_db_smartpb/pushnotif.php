<?php
require_once 'conn.php';
$sql = "SELECT MAX(id_logs) MAX FROM v_disasterlogs_all";

$response = mysqli_query($conn, $sql);

if ( mysqli_num_rows($response) > 0) {
  $row = mysqli_fetch_assoc($response);
  $current = intval($row['MAX']);
  $file = 'idlogs.txt';
  $old = intval(file_get_contents($file));

  if ($current > $old) {

    $newSql = "SELECT * FROM v_disasterlogs_all WHERE id_logs > '.$old.' ORDER BY id_logs ASC";
    $newResponse = mysqli_query($conn, $newSql);

    if ( mysqli_num_rows($newResponse) > 0) {
      while( $newRow = mysqli_fetch_assoc($newResponse)) {
        $regency = explode(" ",$newRow['regency_city']);
        $date_time = explode(" ",$newRow['eventdate']);
        $date = explode("-",$date_time[0]);
        $to = "/topics/disaster";
        $data = array(
          'title' => $newRow['disastertype'],
          'body' => "Telah terjadi ".$newRow['disastertype']." di ".$regency[1]." ".$regency[0]." pada tanggal ".$date[2]."-".$date[1]."-".$date[0].", pukul ".$date_time[1]." WIB",
          'id' =>  $newRow['id_logs']
        );

        pushNotif($to,$data);
      }
    }
    file_put_contents($file, $current);
  }
}



function pushNotif($to,$data) {
  $apiKey = "AAAAC5AE-_s:APA91bE4y6B266rw1BXyFOKel1HgNIT8YdMqzYv_KiJd1C5g4OmsjXHomxoHS8yB5RYPz4f0pZz3z9ObhNlOcv-mkJCHd80fJdaxGfLc7vx80ipoYvLnJdh7GaXxh1TGX8_BXpMX0WRp";

  $ch = curl_init();

  $url = "https://fcm.googleapis.com/fcm/send";

  $fields = json_encode(array('to'=>$to, 'data'=>$data));

  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
  curl_setopt($ch, CURLOPT_POST, 1);
  curl_setopt($ch, CURLOPT_POSTFIELDS,$fields);

  $headers = array();
  $headers[] = 'Authorization: key = '.$apiKey;
  $headers[] = 'Content-Type: application/json';
  curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

  $result = curl_exec($ch);
  if (curl_errno($ch)) {
      echo 'Error:' . curl_error($ch);
  }
  echo $result;
  curl_close($ch);
}
?>