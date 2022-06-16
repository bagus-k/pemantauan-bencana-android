<?php

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id_logs = intval($_POST['id_logs']); 
    $date_time = $_POST['date_time'];
    $disaster_type = intval($_POST['disaster_type']);
    $regency = intval($_POST['regency']);
    $area = $_POST['area'];
    $latitude = floatval($_POST['latitude']);
    $longitude = floatval($_POST['longitude']);
    $weather = $_POST['weather'];
    $chronology = $_POST['chronology'];
    $dead = intval($_POST['dead']);
    $missing = intval($_POST['missing']);
    $serious_wound = intval($_POST['serious_wound']);
    $minor_injuries = intval($_POST['minor_injuries']);
    $damage = $_POST['damage'];
    $losses = $_POST['losses'];
    $response = $_POST['response'];
    $source = $_POST['source'];
    $status = $_POST['status'];
    $level = $_POST['level'];
    $operator_id = $_POST['operator_id'];


    require_once 'conn.php';

    $sql = "UPDATE tb_disasterlogs SET id_disastertype=$disaster_type, eventdate='".$date_time."',regency=$regency,
            area='".$area."',latitude=$latitude,longitude=$longitude,weather='".$weather."',chronology='".$chronology."',
            dead=$dead,missing=$missing,serious_wound=$serious_wound,minor_injuries=$minor_injuries,
            damage='".$damage."',losses='".$losses."',response='".$response."',source='".$source."',status='".$status."',level='".$level."',operator_id='".$operator_id."' WHERE id_logs=$id_logs";

    $response = mysqli_query($conn, $sql);


    if ($response) {
        $result = array();

        $tmp = array();
        $tmp['message'] = "Update Berhasil";
        $result[] = $tmp;

        $status = "1";
        $message = "success";
        echo json_encode(array('status'=>$status, 'message'=>$message, 'data'=>$result), JSON_PRETTY_PRINT);
        mysqli_close($conn);
    }else {
        $status = "0";
        $message = "error";
        $result = array();
        $tmp = array();
        $tmp['message'] = "Update Gagal";
        $result[] = $tmp;
        echo json_encode(array('status'=>$status, 'message'=>$message, 'data'=>$result), JSON_PRETTY_PRINT);
    }
}

?>