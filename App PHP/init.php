<?php

$host = "localhost";
$db = "id12544641_fyp";
$user = "id12544641_matthew";
$password = "matthew";

$con = mysqli_connect($host, $user, $password, $db);
	
if(!$con)
{
	//die("Error in connection!" . mysqli_connect_error());
}
else
{
	//echo "Connection Success...";
}
	
?>