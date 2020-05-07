<?php

    require "init.php";

	$idNumber = $_POST["idNum"];
	$password = $_POST["password"];
	
	$sql = "select * from user_info WHERE idNum = '$idNumber' AND password = '$password'";
	
	$result = mysqli_query($con,$sql);
	
	$sql1 = "select accountType from user_info WHERE idNum = '$idNumber' AND password = '$password'";
	$result1 = mysqli_query($con,$sql1);
	$row = mysqli_fetch_assoc($result1);
	$userType = $row['accountType'];
	
	if(mysqli_num_rows($result)>0)
	{	
		$response = array();
		$code = "login_true";
		$row = mysqli_fetch_array($result);
		$name = $row[0];
		$message = "Login Successful";
		
		//Push variables $code and $message into an array with user type
		array_push($response,array("code"=>$code,"message"=>$message,"userType"=>$userType));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		$response = array();
		$code = "login_false";
		$message = "Please enter the correct login details and try again.";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>	