<?php

    require "init.php";
	
	$classType = $_POST["classType"];
	$modId = $_POST["modID"];
	$courseCode = $_POST["courseCode"];
	$room = $_POST["room"];
	$signInCode = $_POST["loginCode"];
	$lecturerID = $_POST["lecID"];
	$startHr = $_POST["startHr"];
	$startMin = $_POST["startMin"];
	$finHr = $_POST["finHr"];
	$finMin = $_POST["finMin"];
	$lateHr = $_POST["lateHr"];
	$lateMin = $_POST["lateMin"];
	$year = $_POST["year"];
	$month = $_POST["month"];
	$day = $_POST["day"];
	
	$sql = "insert into class_details(classType,moduleID,courseCode,room,signInCode,lecID,startHr,
	startMin,finHr,finMin,lateHr,lateMin,year,month,day) VALUES ('$classType','$modId','$courseCode',
	'$room','$signInCode','$lecturerID','$startHr','$startMin','$finHr','$finMin','$lateHr','$lateMin',
	'$year','$month','$day')";
	
	$insertresult = mysqli_query($con,$sql);
	
	if($insertresult)
	{
		$response = array();
		$code = "class_created";
		$message = "Your new class was created successfully.";
					
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	else
	{
		$response = array();
		$code = "class_creation_error";
		$message = "Server Error. Try again";
		
		//Push variables $code and $message into an array
		array_push($response,array("code"=>$code,"message"=>$message));
		
		//Put array into a JSON object to push to the app
		echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>