<?php

    require "init.php"; 
    
	$classID = $_POST["classID"];
    $studentID = $_POST["studentID"];
	$attended = $_POST["attended"];
	
	$sql = "insert into attendance_records values ('$classID','$studentID','$attended')";
	$insertresult = mysqli_query($con,$sql);
	
	if(!$insertresult)
	{
		$response = array();
		$code = "signIn_false";
		$message = "Server Error. Try again";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
	    $response = array();
		$code = "signIn_true";
		$message = "Class Sign-In successful.";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>	