<?php
date_default_timezone_set('Europe/Moscow');
error_reporting(E_ALL);
ini_set('display_errors', 'Off');

$startTime = microtime(true);

function lite_json_print(array $result) {
    $answer = "{\n";
    foreach ($result as $key => $item) {
        $answer .= "\"";
        $answer .= $key;
        $answer .= "\":\"";
        $answer .= $item;
        $answer .= "\",";
    }
    $answer = substr($answer, 0, strlen($answer) - 1);
    $answer .= "\n}";
    echo $answer;
}


function checkArgs($x, $y, $r) {
    if (strripos($x, ".") !== FALSE) return FALSE;
    if (strripos($y, ".") !== FALSE) {
        $y = rtrim($y, "0");
        $y = rtrim($y, ".");
        $dot_pos = strripos($y, ".");
        if (substr($y, 0, max(0, $dot_pos)) == -5 ||
            substr($y, 0, max(0, $dot_pos)) == 3) {
            return false;
        }
    }
    return
        !is_null($x) && !is_null($y) && !is_null($r) &&
        in_array($x, ["-3", "-2", "-1", "0", "1", "2", "3", "4", "5"], true) &&
        in_array($r, ["1", "1.5", "2", "2.5", "3"], true) &&
        is_numeric($x) &&
        is_numeric($y) && $y >= -5 && $y <= 3 &&
        is_numeric($r);
}

function atArea($x, $y, $r) {
    return
        (($x <= 0) && ($y >= 0) && ($x ** 2 + $y ** 2 <= $r ** 2)) || // Четверть круга
        (($x <= 0) && ($y <= 0) && (2 * $y >= -$x - $r)) || // Треугольник
        (($x >= 0) && ($x <= $r) && ($y <= 0) && ($y >= -$r)); // Прямоугольник
}

$x = isset($_GET["x"]) ? $_GET["x"] : null;
$y = isset($_GET["y"]) ? str_replace(",", ".", $_GET["y"]) : null;
$r = isset($_GET["r"]) ? str_replace(",", ".", $_GET["r"]) : null;

if (!checkArgs($x, $y, $r)) {
    http_response_code(400);
    return;
}
$coordinatesAtArea = atArea($x, $y, $r);

$currentTime = date("H:i:s");
$time = number_format(microtime(true) - $startTime, 10, ".", "") * 1000000;
$result = [
    'x' => $x,
    'y' => $y,
    'r' => $r,
    'currentTime' => $currentTime,
    'time' => (int)$time,
    'atArea' => $coordinatesAtArea ? "Попадание" : "Промах",
];


lite_json_print($result);